package proyectoFinalApi.proyectoFinalApi.controladores;

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

@RestController
@RequestMapping("/api/login")
public class LoginUsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
   
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

            // Verificamos si realmente obtenemos un usuario de la base de datos
            UsuarioDao usuarioDao = usuarioServicio.obtenerUsuarioPorCorreo(usuarioDto.getCorreoUsuario());

            if (usuarioDao == null) {
                System.out.println(" X ERROR: No se encontró el usuario en la bbdd.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

            // Verificamos si el ID del usuario es null
            Long idUsuario = usuarioDao.getIdUsuario();
            if (idUsuario == null) {
                System.out.println(" X ERROR: El id del usuario es null. Revisa la bbdd.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el id del usuario.");
            }

            // Guardamos el ID en la sesión y verificamos que se almacene
            session.setAttribute("idUsuario", idUsuario);
            System.out.println(" El id del usuario se ha guardado en la sesión: " + session.getAttribute("idUsuario"));

            return ResponseEntity.status(200).body(esAdmin);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
    
 // Endpoint para obtener el id del usuario dado correo y contraseña
    @GetMapping("/id")
    public ResponseEntity<String> obtenerIdUsuario(@RequestParam("correoUsuario") String correoUsuario,
                                                    @RequestParam("contraseniaUsuario") String contraseniaUsuario) {
        try {
            UsuarioDao usuario = usuarioServicio.obtenerUsuarioPorCorreo(correoUsuario);
            if (usuario != null) {
                return ResponseEntity.ok(String.valueOf(usuario.getIdUsuario()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

}