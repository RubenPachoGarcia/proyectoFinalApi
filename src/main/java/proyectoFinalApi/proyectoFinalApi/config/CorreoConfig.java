package proyectoFinalApi.proyectoFinalApi.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Configuración para el envío de correos electrónicos.
 * Esta clase define el bean {@link JavaMailSender} utilizado para enviar correos a través de SMTP.
 */
@Configuration
public class CorreoConfig {

    /**
     * Crea y configura un bean de {@link JavaMailSender} para el envío de correos electrónicos.
     */
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("muletaymontera@gmail.com"); 
        mailSender.setPassword("asqy zerb tjtj wyeg");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
