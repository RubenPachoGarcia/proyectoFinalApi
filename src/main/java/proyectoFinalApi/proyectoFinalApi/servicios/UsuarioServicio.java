package proyectoFinalApi.proyectoFinalApi.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import proyectoFinalApi.proyectoFinalApi.daos.UsuarioDao;
import proyectoFinalApi.proyectoFinalApi.db.DBConexion;
import proyectoFinalApi.proyectoFinalApi.dtos.RegistroUsuarioDto;
import proyectoFinalApi.proyectoFinalApi.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

	// Simula el almacenamiento de tokens
	private Map<String, String> tokens = new HashMap<>();

	public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}
	
	public List<UsuarioDao> obtenerTodosLosUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public UsuarioDao obtenerUsuarioPorEmail(String correoUsuario) {
        return usuarioRepositorio.findByCorreoUsuario(correoUsuario);
    }
	
	@Autowired
    private PasswordEncoder encriptacion;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private JavaMailSender mailSender;

	public ResponseEntity<String> validarDatos(String correoUsuario, String contraseniaUsuario) {
		UsuarioDao usuarioDao = usuarioRepositorio.findByCorreoUsuario(correoUsuario);

		if (usuarioDao == null) {
			return ResponseEntity.status(401).body("Datos incorrectos");
		}

		if (!encriptacion.matches(contraseniaUsuario, usuarioDao.getContraseniaUsuario())) {
			return ResponseEntity.status(401).body("Datos incorrectos");
		}

		return ResponseEntity.ok(usuarioDao.getEsAdmin());
	}

	public boolean correoUsuarioExiste(String correoUsuario) {
		return usuarioRepositorio.existsByCorreoUsuario(correoUsuario);
	}

	public void registroUsuario(RegistroUsuarioDto usuarioDto) {
		if (usuarioDto.getCorreoUsuario() == null || usuarioDto.getCorreoUsuario().isEmpty()) {
			throw new IllegalArgumentException("El correo es obligatorio.");
		}

		UsuarioDao usuarioDao = new UsuarioDao();
		
		usuarioDao.setNombreCompletoUsuario(usuarioDto.getNombreCompletoUsuario());
		usuarioDao.setCorreoUsuario(usuarioDto.getCorreoUsuario());
		usuarioDao.setTelefonoUsuario(usuarioDto.getTelefonoUsuario());
		usuarioDao.setFotoUsuario(usuarioDto.getFotoUsuario());
		usuarioDao.setContraseniaUsuario(usuarioDto.getContraseniaUsuario());
		usuarioDao.setEsAdmin("false");
		usuarioDao.setEsPremium("false");

		usuarioRepositorio.save(usuarioDao);
		
		// Enviamos el correo de confirmación
	    enviarCorreoConfirmacion(usuarioDto.getCorreoUsuario(), usuarioDto.getNombreCompletoUsuario());
	}
	
	// Método para enviar un correo de confirmación
	private void enviarCorreoConfirmacion(String correoUsuario, String nombreUsuario) {
	    String asunto = "Bienvenido a MuletaYMontera";
	    String mensaje = "<h1>¡Bienvenido " + nombreUsuario + "!</h1>"
	                   + "<p>Tu registro en MuletaYMontera se ha realizado con éxito.</p>"
	                   + "<p>Ahora puedes iniciar sesión con tu correo y contraseña.</p>"
	                   + "<br>"
	                   + "<p>Gracias por unirte a nuestra comunidad.</p>";

	    try {
	        enviarCorreo(correoUsuario, asunto, mensaje);
	        System.out.println("Correo de confirmación enviado a: " + correoUsuario);
	    } catch (Exception e) {
	        System.out.println("Error al enviar el correo de confirmación: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public boolean enviarCorreoRecuperacion(String correoUsuario) {
		// Verificamos si el usuario existe
		if (!usuarioRepositorio.existsByCorreoUsuario(correoUsuario)) {
			System.out.println("Error: No se encontró el correo en la base de datos.");
			return false;
		}

		// Generamos el token y se almacena
		String token = UUID.randomUUID().toString();
		tokens.put(token, correoUsuario);

		// Construimos la URL de recuperación de correo
		String urlCorreoRecuperacion = "http://localhost:1180/proyectoFinalFront/recuperarContrasenia.jsp?token="
				+ token;

		// Mensaje del correo
		String mensaje = "<p>Haga clic en el siguiente enlace para cambiar su contraseña en MuletaYMontera:</p>"
				+ "<p><a href= '" + urlCorreoRecuperacion + "'>Restablecer contraseña</a></p>"
				+ "<p>Si no solicitó este cambio, ignore este mensaje.</p>";

		// Enviamos el correo
		try {
			enviarCorreo(correoUsuario, "Restablecer contraseña", mensaje);
			System.out.println("Correo de recuperación enviado a: " + correoUsuario);
			return true;
		} catch (Exception e) {
			System.out.println("Error al enviar el correo: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean cambiarContrasenia(String token, String nuevaContraseniaUsuario) {
		if (tokens.containsKey(token)) {
			String correoUsuario = tokens.get(token);
			// Encripta la nueva contraseña antes de guardarla en la bbdd
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String contraseniaEncriptada = passwordEncoder.encode(nuevaContraseniaUsuario);
			// Guarda la contraseña encriptada en la bbdd
			usuarioRepositorio.actualizarContrasenia(correoUsuario, contraseniaEncriptada);
			// Elimina el token después de usarlo
			tokens.remove(token);
			return true;
		}
		return false;
	}

	private void enviarCorreo(String correoDestinatario, String correoAsunto, String correoCuerpo) {
		// Verificar que el correo destinatario no sea nulo o vacío
		if (correoDestinatario == null || correoDestinatario.isEmpty()) {
			System.out.println("Error: El correo destinatario es nulo o vacío.");
			return;
		}

		// Verificar que el asunto y cuerpo no sean nulos o vacíos
		if (correoAsunto == null || correoAsunto.isEmpty()) {
			System.out.println("Error: El asunto del correo es nulo o vacío.");
			return;
		}

		if (correoCuerpo == null || correoCuerpo.isEmpty()) {
			System.out.println("Error: El cuerpo del correo es nulo o vacío.");
			return;
		}

		// Verificar que mailSender no sea null
		if (mailSender == null) {
			System.out.println("Error: El mailSender no está configurado correctamente.");
			return;
		}

		try {
			MimeMessage mensaje = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
			helper.setTo(correoDestinatario);
			helper.setSubject(correoAsunto);
			// true indica que el cuerpo es HTML
			helper.setText(correoCuerpo, true);

			// Enviamos el correo
			mailSender.send(mensaje);
			System.out.println("Correo enviado correctamente a " + correoDestinatario);
		} catch (MessagingException e) {
			// Manejo de la excepción para que de mas detalles sobre los errores
			System.out.println("Error al enviar el correo: " + e.getMessage());
			// Imprime la traza completa para depurar
			e.printStackTrace();
		}
	}
	
	public boolean verificarCorreoExistente(String correoUsuario) {
       
		boolean correoExistente = false;
        
		try (Connection con = DBConexion.getConnection()) {
            String consultaCorreoExistente = "SELECT COUNT(*) FROM proyecto.usuarios WHERE correo_usuario = ?";
            
            try (PreparedStatement stmt = con.prepareStatement(consultaCorreoExistente)) {
                stmt.setString(1, correoUsuario);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    
                	if (rs.next() && rs.getInt(1) > 0) {
                		correoExistente = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correoExistente;
    }

    public boolean verificarTelefonoExistente(String telefonoUsuario) {
        
    	boolean telefonoExistente = false;
        
    	try (Connection con = DBConexion.getConnection()) {
            String consultaTelefonoExistente = "SELECT COUNT(*) FROM proyecto.usuarios WHERE telefono_usuario = ?";
            
            try (PreparedStatement stmt = con.prepareStatement(consultaTelefonoExistente)) {
                stmt.setString(1, telefonoUsuario);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    
                	if (rs.next() && rs.getInt(1) > 0) {
                		telefonoExistente = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return telefonoExistente;
    }
    
    public UsuarioDao obtenerUsuarioPorCorreo(String correoUsuario) {
        return usuarioRepositorio.findByCorreoUsuario(correoUsuario);
    }
    
    public boolean eliminarUsuario(Long idUsuario) {
        if (usuarioRepositorio.existsById(idUsuario)) {
            usuarioRepositorio.deleteById(idUsuario);
            System.out.println("Usuario con ID " + idUsuario + " eliminado correctamente.");
            return true;
        } else {
            System.err.println("Usuario con ID " + idUsuario + " no encontrado.");
            return false;
        }
    }
}

/*
 * Aquí se implementa el envío de correo con una biblioteca como JavaMail
 * System.out.println("Enviando correo a: " + correoDestinatario);
 * System.out.println("Asunto: " + correoAsunto); System.out.println("Cuerpo: "
 * + correoCuerpo);
 */
