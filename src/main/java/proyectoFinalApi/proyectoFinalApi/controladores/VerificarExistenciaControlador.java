package proyectoFinalApi.proyectoFinalApi.controladores;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyectoFinalApi.proyectoFinalApi.dtos.VerificarExistenciaDto;
import proyectoFinalApi.proyectoFinalApi.servicios.UsuarioServicio;

/**
 * Controlador para verificar la existencia de correos y teléfonos en la base de datos.
 */
@CrossOrigin(origins = "http://localhost:1180")
@RestController
@RequestMapping("/api/verificar")
public class VerificarExistenciaControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Verifica si un correo ya está registrado en la base de datos.
     * 
     * @param dto DTO con el correo del usuario a verificar.
     * @return Un map con la clave "existe" y un valor booleano indicando si el correo existe.
     */
    @PostMapping("/correo")
    public ResponseEntity<Map<String, Boolean>> verificarCorreo(@RequestBody VerificarExistenciaDto dto) {
        try {
            boolean correoExiste = usuarioServicio.verificarCorreoExistente(dto.getCorreoUsuario());
            Map<String, Boolean> response = new HashMap<>();
            response.put("existe", correoExiste);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", true));
        }
    }

    /**
     * Verifica si un número de teléfono ya está registrado en la base de datos.
     * 
     * @param dto DTO con el teléfono del usuario a verificar.
     * @return Un map con la clave "existe" y un valor booleano indicando si el teléfono existe.
     */
    @PostMapping("/telefono")
    public ResponseEntity<Map<String, Boolean>> verificarTelefono(@RequestBody VerificarExistenciaDto dto) {
        try {
            boolean telefonoExiste = usuarioServicio.verificarTelefonoExistente(dto.getTelefonoUsuario());
            Map<String, Boolean> response = new HashMap<>();
            response.put("existe", telefonoExiste);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", true));
        }
    }
}
