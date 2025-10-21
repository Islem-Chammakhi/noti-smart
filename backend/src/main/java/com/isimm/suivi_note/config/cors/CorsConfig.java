package com.isimm.suivi_note.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:3000")); // React default port
        corsConfig.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "ACCEPT")); // I think ACCEPT belong to SSE methods, not sure
        corsConfig.setAllowedHeaders(List.of("Authorization", "Content-type"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlSource = new UrlBasedCorsConfigurationSource();
        urlSource.registerCorsConfiguration("/api/**", corsConfig);


        return urlSource;

    }
}
