package proyectoFinalApi.proyectoFinalApi.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase que activa la seguridad de toda la aplicacion
 */
@Configuration
@EnableWebSecurity  
public class SeguridadConfig {
	
	/**
	 * Metodo de encriptacion de datos
	 */
	@Bean
    public PasswordEncoder encriptacion() {
        return new BCryptPasswordEncoder();
    }

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
