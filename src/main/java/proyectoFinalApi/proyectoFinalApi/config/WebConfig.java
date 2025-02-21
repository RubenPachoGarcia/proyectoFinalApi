package proyectoFinalApi.proyectoFinalApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase para el manejo de Cross-Origin Resource Sharing. Permite configurar las
 * políticas de acceso entre el backend y frontend.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			/**
			 * Configura las reglas de CORS para la aplicación.
			 * 
			 * @param registry registro de CORS donde se definen las reglas
			 */
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Aplica las reglas a todas las rutas "/**"
						.allowedOrigins("http://localhost:1180") // Permite solicitudes desde el frontend en											// localhost:1180
						.allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
						.allowedHeaders("*") // Permite todos los encabezados en la solicitud
						.allowCredentials(true); // Permite el uso de cookies o credenciales en las solicitudes
			}
		};
	}
}
