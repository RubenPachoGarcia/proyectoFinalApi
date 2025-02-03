package proyectoFinalApi.proyectoFinalApi.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import proyectoFinalApi.proyectoFinalApi.dtos.RecuperarContraseniaDto;
import proyectoFinalApi.proyectoFinalApi.servicios.UsuarioServicio;

@RestController
// Permite las solicitudes desde el frontend
@CrossOrigin(origins = "http://localhost:1180") 
@RequestMapping("/api/recuperar")
public class RecuperarContraseniaControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;

	@PostMapping("/solicitar")
	public String solicitarRecuperacion(@RequestBody RecuperarContraseniaDto recuperarContraseniaUsuarioDto) {
		boolean enviado = usuarioServicio.enviarCorreoRecuperacion(recuperarContraseniaUsuarioDto.getCorreoUsuario());
		if (enviado) {
			return "Correo de recuperación enviado.";
		} else {
			return "Error: No se encontró el correo proporcionado.";
		}
	}

	// Este método maneja la solicitud POST para cambiar la contraseña
	@PostMapping("/cambiar")
	public ResponseEntity<String> cambiarContrasenia(@RequestParam String token, @RequestParam String nuevaContrasenia) {
		String urlLogin = "http://localhost:1180/proyectoFinalFront/login.jsp";
	    boolean cambiado = usuarioServicio.cambiarContrasenia(token, nuevaContrasenia);
	    if (cambiado) {
	        return ResponseEntity.ok("Contraseña cambiada correctamente." + "<p><a href= '" + urlLogin + "'>Login</a></p>");
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Token inválido o expirado.");
	    }
	}
}
