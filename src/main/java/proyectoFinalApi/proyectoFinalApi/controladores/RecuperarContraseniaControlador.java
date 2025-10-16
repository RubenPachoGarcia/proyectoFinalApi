package proyectoFinalApi.proyectoFinalApi.controladores;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import proyectoFinalApi.proyectoFinalApi.dtos.RecuperarContraseniaDto;
import proyectoFinalApi.proyectoFinalApi.servicios.UsuarioServicio;

/**
 * Controlador para la recuperación de contraseñas de usuarios.
 */
@RestController
@CrossOrigin(origins = "http://localhost:1180") // Permite solicitudes desde el frontend
@RequestMapping("/api/recuperar")
public class RecuperarContraseniaControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    /**
     * Solicita la recuperación de contraseña enviando un correo al usuario.
     * @param recuperarContraseniaUsuarioDto DTO con el correo del usuario.
     * @return Mensaje indicando si el correo fue enviado con éxito o no.
     */
    @PostMapping("/solicitar")
    public String solicitarRecuperacion(@RequestBody RecuperarContraseniaDto recuperarContraseniaUsuarioDto) {
        boolean enviado = usuarioServicio.enviarCorreoRecuperacion(recuperarContraseniaUsuarioDto.getCorreoUsuario());
        return enviado ? "Correo de recuperación enviado." : "Error: No se encontró el correo proporcionado.";
    }

    /**
     * Cambia la contraseña de un usuario si el token de recuperación es válido.
     * @param token           Token de recuperación de contraseña.
     * @param nuevaContrasenia Nueva contraseña a establecer.
     * @return Respuesta indicando si el cambio fue exitoso o no.
     */
    
    @PostMapping("/cambiar")
    public void cambiarContrasenia(@RequestParam String token, 
                                   @RequestParam String nuevaContrasenia, 
                                   HttpServletResponse response) throws IOException {
        boolean cambiado = usuarioServicio.cambiarContrasenia(token, nuevaContrasenia);

        if (cambiado) {
            response.sendRedirect("http://localhost:8080/proyectoFinalFront/exitoCambioContrasenia.jsp");
        } 
    }

	/*
	 * @PostMapping("/cambiar") public ResponseEntity<String>
	 * cambiarContrasenia(@RequestParam String token, @RequestParam String
	 * nuevaContrasenia) { String urlLogin =
	 * "http://localhost:8080/proyectoFinalFront/login.jsp"; boolean cambiado =
	 * usuarioServicio.cambiarContrasenia(token, nuevaContrasenia); return cambiado
	 * ? ResponseEntity.ok("Contraseña cambiada correctamente." + "<p><a href= '" +
	 * urlLogin + "'>Login</a></p>") :
	 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).
	 * body("Error: Token inválido o expirado."); }
	 */
}
