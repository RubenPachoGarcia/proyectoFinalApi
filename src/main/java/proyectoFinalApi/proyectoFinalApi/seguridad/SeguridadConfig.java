package proyectoFinalApi.proyectoFinalApi.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // Activa la seguridad web para la aplicación
public class SeguridadConfig {

    /**
     * Configuración de seguridad para la aplicación.
     */
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // Permitir acceso a todas las rutas
            )
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF (opcional)
            .formLogin(login -> login.disable()) // Deshabilitar el formulario de login
            .logout(logout -> logout.disable()); // Deshabilitar logout (opcional)

        return http.build();
    }
}
