package proyectoFinalApi.proyectoFinalApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS para la aplicación.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura los mapeos de CORS para permitir solicitudes desde el frontend.
     *
     * @return un WebMvcConfigurer con las reglas de CORS configuradas.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")                // Aplica a todas las rutas
                        .allowedOrigins("http://localhost:1180") // Permite solicitudes desde este origen
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                        .allowedHeaders("*")           // Permite todos los encabezados
                        .allowCredentials(true);       // Permite el uso de credenciales
            }
        };
    }
}
