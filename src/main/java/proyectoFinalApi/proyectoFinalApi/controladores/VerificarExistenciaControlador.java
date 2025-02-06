package proyectoFinalApi.proyectoFinalApi.controladores;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import proyectoFinalApi.proyectoFinalApi.dtos.VerificarExistenciaDto;
import proyectoFinalApi.proyectoFinalApi.servicios.UsuarioServicio;

public class VerificarExistenciaControlador extends HttpServlet {

	private UsuarioServicio usuarioServicio;

	@Override
	public void init() {
		this.usuarioServicio = new UsuarioServicio();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Leer datos JSON del body de la petición
		ObjectMapper mapper = new ObjectMapper();
		VerificarExistenciaDto dto = mapper.readValue(request.getReader(), VerificarExistenciaDto.class);

		// Verificar correo y teléfono
		boolean correoExiste = usuarioServicio.verificarCorreoExistente(dto.getCorreoUsuario());
		boolean telefonoExiste = usuarioServicio.verificarTelefonoExistente(dto.getTelefonoUsuario());

		// Crear respuesta
		if (correoExiste || telefonoExiste) {
			response.setStatus(HttpServletResponse.SC_OK);
			// Existe uno como minimo
			response.getWriter().write("{ \"existe\": true }");
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
			// Ninguno existe
			response.getWriter().write("{ \"existe\": false }");
		}
	}
}
