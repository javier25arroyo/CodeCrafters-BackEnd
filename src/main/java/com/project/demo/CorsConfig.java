package com.project.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para la aplicación.
 * Permite peticiones desde orígenes específicos, como el frontend de Angular en desarrollo.
 */
@Configuration
public class CorsConfig {

    /**
     * Constructor por defecto.
     */
    public CorsConfig() {
    }

    /**
     * Define un filtro de CORS para permitir peticiones desde el frontend.
     *
     * @return Un {@link CorsFilter} configurado para permitir el origen de localhost:4200.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
