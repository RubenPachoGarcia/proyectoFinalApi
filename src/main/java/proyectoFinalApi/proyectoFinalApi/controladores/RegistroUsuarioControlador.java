package proyectoFinalApi.proyectoFinalApi.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyectoFinalApi.proyectoFinalApi.dtos.RegistroUsuarioDto;
import proyectoFinalApi.proyectoFinalApi.servicios.UsuarioServicio;

@RestController
@RequestMapping("/api/registro")
public class RegistroUsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/usuario")
    public ResponseEntity<String> registroUsuario(@RequestBody RegistroUsuarioDto usuarioDto) {
        try {
            if (usuarioDto.getCorreoUsuario() == null || usuarioDto.getCorreoUsuario().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo es obligatorio.");
            }

            if (usuarioServicio.correoUsuarioExiste(usuarioDto.getCorreoUsuario())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya existe.");
            }

            usuarioServicio.registroUsuario(usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR interno del servidor.");
        }
    }
}