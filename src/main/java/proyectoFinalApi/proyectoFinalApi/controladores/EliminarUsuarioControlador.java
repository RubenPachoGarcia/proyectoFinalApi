package proyectoFinalApi.proyectoFinalApi.controladores;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import proyectoFinalApi.proyectoFinalApi.daos.UsuarioDao;
import proyectoFinalApi.proyectoFinalApi.repositorios.UsuarioRepositorio;

/**
 * Controlador REST para operaciones sobre usuarios.
 * Proporciona endpoints para listar y eliminar usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:8080")
public class EliminarUsuarioControlador {

    private static final Logger logger = LoggerFactory.getLogger(EliminarUsuarioControlador.class);
    private final UsuarioRepositorio usuarioRepositorio;

    /**
     * Constructor que inyecta el repositorio de usuarios.
     * @param usuarioRepositorio repositorio para gestionar usuarios.
     */
    public EliminarUsuarioControlador(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    /**
     * Endpoint para obtener la lista de usuarios.
     * @return ResponseEntity que contiene la lista de usuarios
     */
    @GetMapping("/lista")
    public ResponseEntity<List<UsuarioDao>> obtenerUsuarios() {
        List<UsuarioDao> listaUsuarios = usuarioRepositorio.findAll(); // Obtiene todos los usuarios de la base de datos
        logger.info("Usuarios obtenidos desde la base de datos: {}", listaUsuarios);

        if (listaUsuarios.isEmpty()) {
            logger.warn("No se encontraron usuarios en la base de datos.");
            return ResponseEntity.noContent().build(); // Devuelve respuesta vacía si no hay usuarios
        }

        return ResponseEntity.ok(listaUsuarios); // Devuelve la lista de usuarios
    }

    /**
     * Endpoint para eliminar un usuario por su correo.
     * @return ResponseEntity con un mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarUsuario(@RequestParam String correoUsuario) {
        try {
            Optional<UsuarioDao> usuario = usuarioRepositorio.findByCorreoUsuario(correoUsuario);
            if (usuario.isPresent()) {
                usuarioRepositorio.delete(usuario.get());
                logger.info("Usuario eliminado con correo: {}", correoUsuario);
                return ResponseEntity.ok("Usuario eliminado correctamente.");
            } else {
                logger.warn("No se encontró usuario con correo: {}", correoUsuario);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error al eliminar usuario: " + correoUsuario, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error interno al eliminar usuario.");
        }
    }
}
