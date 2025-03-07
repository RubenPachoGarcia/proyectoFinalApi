package proyectoFinalApi.proyectoFinalApi.controladores;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import proyectoFinalApi.proyectoFinalApi.daos.UsuarioDao;
import proyectoFinalApi.proyectoFinalApi.dtos.LoginUsuarioDto;
import proyectoFinalApi.proyectoFinalApi.servicios.UsuarioServicio;

/**
 * Controlador para la autenticación de usuarios.
 * Maneja el inicio de sesión y la obtención del id del usuario.
 */
@RestController
@RequestMapping("/api/login")
public class LoginUsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Autentica a un usuario verificando su correo y contraseña.
     * Almacena en la sesión si es admin y su id.
     * @param usuarioDto Datos de acceso del usuario.
     * @param session    Sesión HTTP donde se almacenan datos de usuario.
     * @return ResponseEntity con mensaje de éxito o error.
     */
    @PostMapping("/usuario")
    public ResponseEntity<String> autenticarUsuario(@RequestBody LoginUsuarioDto usuarioDto, HttpSession session) {
        try {
            System.out.println(" Correo: " + usuarioDto.getCorreoUsuario());
            System.out.println(" Contraseña: " + usuarioDto.getContraseniaUsuario());

            ResponseEntity<String> resultado = usuarioServicio.validarDatos(usuarioDto.getCorreoUsuario(), usuarioDto.getContraseniaUsuario());

            if (resultado.getStatusCodeValue() == 401) {
                return ResponseEntity.status(401).body("ERROR: Usuario o contraseña incorrectos.");
            }

            String esAdmin = resultado.getBody().trim();
            session.setAttribute("esAdmin", esAdmin.equals("true") ? "true" : "false");

            Optional<UsuarioDao> usuarioDao = usuarioServicio.obtenerUsuarioPorCorreo(usuarioDto.getCorreoUsuario());

            if (!usuarioDao.isPresent()) {
                System.out.println(" X ERROR: No se encontró el usuario en la bbdd.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

            Long idUsuario = usuarioDao.get().getIdUsuario();
            if (idUsuario == null) {
                System.out.println(" X ERROR: El id del usuario es null. Revisa la bbdd.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el id del usuario.");
            }

            session.setAttribute("idUsuario", idUsuario);
            System.out.println(" El id del usuario se ha guardado en la sesión: " + session.getAttribute("idUsuario"));

            return ResponseEntity.status(200).body(esAdmin);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    /**
     * Obtiene el ID del usuario según su correo y contraseña.
     * @param correoUsuario     Correo del usuario.
     * @param contraseniaUsuario Contraseña del usuario.
     * @return ResponseEntity con el id del usuario.
     */
    @GetMapping("/id")
    public ResponseEntity<String> obtenerIdUsuario(@RequestParam("correoUsuario") String correoUsuario,
                                                   @RequestParam("contraseniaUsuario") String contraseniaUsuario) {
        try {
            Optional<UsuarioDao> usuario = usuarioServicio.obtenerUsuarioPorCorreo(correoUsuario);
            if (usuario.isPresent()) {
                return ResponseEntity.ok(String.valueOf(usuario.get().getIdUsuario()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}
