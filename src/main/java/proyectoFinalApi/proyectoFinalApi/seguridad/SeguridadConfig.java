package proyectoFinalApi.proyectoFinalApi.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración para la seguridad de la aplicación.
 * <p>
 * En esta clase se define el comportamiento para la codificación y verificación de contraseñas en la aplicación
 * utilizando el algoritmo de hash BCrypt, proporcionado por Spring Security.
 * </p>
 */
@Configuration
public class SeguridadConfig {

    /**
     * Método para encriptar las contraseñas.
     * El PasswordEncoder se utiliza para encriptar contraseñas de forma segura antes de que llegen a la bbdd.
     */
    @Bean
    public PasswordEncoder encriptacion() {
        // Se devuelve una instancia que proporcionará una contraseña encriptada
        return new BCryptPasswordEncoder();
    }
}
