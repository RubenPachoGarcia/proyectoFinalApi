package proyectoFinalApi.proyectoFinalApi.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // Activa la seguridad web para la aplicación
public class SeguridadConfig {

    /**
     * Método para encriptar las contraseñas.
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
        // Configuración de seguridad
        http
            .csrf().disable()  // Desactiva CSRF (si lo necesitas puedes mantenerlo)
            .authorizeRequests()
                .requestMatchers("/api/**").permitAll() // Permite el acceso sin autenticación a las rutas /api/**
                .anyRequest().authenticated() // Requiere autenticación para todas las demás rutas
            .and()
            .formLogin()
                .permitAll() // Permite el formulario de login
            .and()
            .logout()
                .permitAll(); // Permite hacer logout

        return http.build(); // Devuelve la configuración de seguridad
    }
}
