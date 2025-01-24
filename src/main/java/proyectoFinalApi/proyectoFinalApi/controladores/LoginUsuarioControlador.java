package proyectoFinalApi.proyectoFinalApi.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyectoFinalApi.proyectoFinalApi.dtos.LoginUsuarioDto;
import proyectoFinalApi.proyectoFinalApi.servicios.UsuarioServicio;

@RestController
@RequestMapping("/api/login")
public class LoginUsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/usuario")
    public ResponseEntity<String> autenticarUsuario(@RequestBody LoginUsuarioDto usuarioDto) {
        try {
            System.out.println("Correo: " + usuarioDto.getCorreoUsuario());
            System.out.println("Contraseña: " + usuarioDto.getContraseniaUsuario());

            ResponseEntity<String> resultado = usuarioServicio.validarDatos(usuarioDto.getCorreoUsuario(), usuarioDto.getContraseniaUsuario());

            if (resultado.getStatusCodeValue() == 401) {
                return ResponseEntity.status(401).body("ERROR: Usuario o contraseña incorrectos.");
            }

            String esAdmin = resultado.getBody().trim();
            if ("true".equals(esAdmin)) {
                return ResponseEntity.status(200).body("true");
            } else if ("false".equals(esAdmin)) {
                return ResponseEntity.status(200).body("false");
            }

            return ResponseEntity.status(401).body("Rol desconocido.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
}