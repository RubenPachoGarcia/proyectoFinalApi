package proyectoFinalApi.proyectoFinalApi.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

/**
 * Servicio que gestiona las operaciones relacionadas con los usuarios.
 */
@Service
public class UsuarioServicio {

    /** Simula el almacenamiento de tokens para recuperación de contraseña. */
    private Map<String, String> tokens = new HashMap<>();

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder encriptacion;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Constructor del servicio de usuario.
     * @param usuarioRepositorio Repositorio de usuarios.
     */
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    /**
     * Obtiene todos los usuarios registrados.
     * @return Lista de usuarios.
     */
    public List<UsuarioDao> obtenerTodosLosUsuarios() {
        return usuarioRepositorio.findAll();
    }

    /**
     * Valida los datos de inicio de sesión del usuario.
     * @param correoUsuario Correo del usuario.
     * @param contraseniaUsuario Contraseña del usuario.
     * @return Respuesta HTTP con el resultado de la validación.
     */
    public ResponseEntity<String> validarDatos(String correoUsuario, String contraseniaUsuario) {
        Optional<UsuarioDao> usuarioDao = usuarioRepositorio.findByCorreoUsuario(correoUsuario);

        if (usuarioDao.isEmpty() || !encriptacion.matches(contraseniaUsuario, usuarioDao.get().getContraseniaUsuario())) {
            return ResponseEntity.status(401).body("Datos incorrectos");
        }

        return ResponseEntity.ok(usuarioDao.get().getEsAdmin());
    }

    /**
     * Verifica si un correo electrónico ya está registrado.
     * @param correoUsuario Correo del usuario.
     * @return true si el correo ya existe, false en caso contrario.
     */
    public boolean correoUsuarioExiste(String correoUsuario) {
        return usuarioRepositorio.existsByCorreoUsuario(correoUsuario);
    }

    /**
     * Registra un nuevo usuario en la aplicación.
     * @param usuarioDto Datos del usuario a registrar.
     */
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
        enviarCorreoConfirmacion(usuarioDto.getCorreoUsuario(), usuarioDto.getNombreCompletoUsuario());
    }

    /**
     * Envía un correo de confirmación tras el registro de un usuario.
     * @param correoUsuario Correo del usuario.
     * @param nombreUsuario Nombre del usuario.
     */
    private void enviarCorreoConfirmacion(String correoUsuario, String nombreUsuario) {
        String asunto = "Bienvenido a MuletaYMontera";
        String mensaje = "<h1>¡Bienvenido " + nombreUsuario + "!</h1>"
                + "<p>Tu registro en MuletaYMontera se ha realizado con éxito.</p>"
                + "<p>Ahora puedes iniciar sesión con tu correo y contraseña.</p>"
                + "<br><p>Gracias por unirte a nuestra comunidad.</p>";

        enviarCorreo(correoUsuario, asunto, mensaje);
    }

    /**
     * Envía un correo para la recuperación de contraseña.
     * @param correoUsuario Correo del usuario.
     * @return true si el correo fue enviado con éxito, false en caso de error.
     */
    public boolean enviarCorreoRecuperacion(String correoUsuario) {
        if (!usuarioRepositorio.existsByCorreoUsuario(correoUsuario)) {
            return false;
        }

        String token = UUID.randomUUID().toString();
        tokens.put(token, correoUsuario);

        String urlCorreoRecuperacion = "http://localhost:1180/proyectoFinalFront/recuperarContrasenia.jsp?token=" + token;
        String mensaje = "<p>Haga clic en el siguiente enlace para cambiar su contraseña:</p>"
                + "<p><a href='" + urlCorreoRecuperacion + "'>Restablecer contraseña</a></p>"
                + "<p>Si no solicitó este cambio, ignore este mensaje.</p>";

        return enviarCorreo(correoUsuario, "Restablecer contraseña", mensaje);
    }

    /**
     * Permite a un usuario cambiar su contraseña mediante un token de recuperación.
     * @param token Token de recuperación.
     * @param nuevaContraseniaUsuario Nueva contraseña del usuario.
     * @return true si la contraseña se cambió exitosamente, false en caso contrario.
     */
    public boolean cambiarContrasenia(String token, String nuevaContraseniaUsuario) {
        if (tokens.containsKey(token)) {
            String correoUsuario = tokens.get(token);
            String contraseniaEncriptada = new BCryptPasswordEncoder().encode(nuevaContraseniaUsuario);
            usuarioRepositorio.actualizarContrasenia(correoUsuario, contraseniaEncriptada);
            tokens.remove(token);
            return true;
        }
        return false;
    }

    /**
     * Envía un correo a un destinatario.
     * @param correoDestinatario Correo del destinatario.
     * @param correoAsunto Asunto del correo.
     * @param correoCuerpo Cuerpo del correo en formato HTML.
     * @return true si el correo fue enviado correctamente, false en caso de error.
     */
    private boolean enviarCorreo(String correoDestinatario, String correoAsunto, String correoCuerpo) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
            helper.setTo(correoDestinatario);
            helper.setSubject(correoAsunto);
            helper.setText(correoCuerpo, true);

            mailSender.send(mensaje);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifica si un correo existe en la base de datos.
     * @param correoUsuario Correo a verificar.
     * @return true si el correo existe, false en caso contrario.
     */
    public boolean verificarCorreoExistente(String correoUsuario) {
        return consultaExiste("SELECT COUNT(*) FROM proyecto.usuarios WHERE correo_usuario = ?", correoUsuario);
    }

    /**
     * Verifica si un teléfono existe en la base de datos.
     * @param telefonoUsuario Teléfono a verificar.
     * @return true si el teléfono existe, false en caso contrario.
     */
    public boolean verificarTelefonoExistente(String telefonoUsuario) {
        return consultaExiste("SELECT COUNT(*) FROM proyecto.usuarios WHERE telefono_usuario = ?", telefonoUsuario);
    }

    private boolean consultaExiste(String consulta, String parametro) {
        try (Connection con = DBConexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(consulta)) {
            stmt.setString(1, parametro);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene un usuario por su correo electrónico.
     * @param correoUsuario Correo del usuario.
     * @return Usuario encontrado (si existe).
     */
    public Optional<UsuarioDao> obtenerUsuarioPorCorreo(String correoUsuario) {
        return usuarioRepositorio.findByCorreoUsuario(correoUsuario);
    }
}