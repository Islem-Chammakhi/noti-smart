package com.isimm.suivi_note.config.email;

import com.isimm.suivi_note.brevo.service.BrevoService;
import com.isimm.suivi_note.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class EmailConfig {
/*
    @Value("${email.key}") String apiKey;

    @Bean
    @ConditionalOnProperty(name = "email.service.provider", havingValue = "brevo")
    public EmailService brevoEmailService() {
        return new BrevoService(apiKey);
    }
*/

    /*@Bean
    @ConditionalOnProperty(name = "email.service.provider", havingValue = "local")
    public EmailService localEmailService() {
        return new LocalEmailService();
    }*/
}
