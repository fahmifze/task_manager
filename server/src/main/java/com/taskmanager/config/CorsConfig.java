package com.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration // Marks this as a Spring configuration class
public class CorsConfig {

    @Bean // Creates a Spring-managed bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allowed frontend origins (React dev servers)
        config.addAllowedOrigin("http://localhost:5173"); // Vite default
        config.addAllowedOrigin("http://localhost:3000"); // Alternative port
        config.addAllowedOrigin("http://127.0.0.1:5173");

        // Allowed HTTP methods
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS"); // Preflight requests

        config.addAllowedHeader("*"); // Allow all headers
        config.setAllowCredentials(true); // Allow cookies/auth

        // Apply CORS config to all /api/* endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
