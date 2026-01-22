package com.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Marks this as a Spring configuration class
@EnableWebSecurity // Enables Spring Security for this application
public class SecurityConfig {

    // Inject our JWT filter
    private final JwtAuthFilter jwtAuthFilter;

    // Constructor injection
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    // Main security configuration - defines which endpoints need authentication
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (Cross-Site Request Forgery) protection
                // We don't need CSRF because we use JWT tokens (stateless)
                .csrf(csrf -> csrf.disable())

                // Configure which endpoints need authentication
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - no login required
                        .requestMatchers("/api/auth/**").permitAll() // Login/register endpoints
                        .requestMatchers("/api/categories/**").permitAll() // Categories (public for now)
                        .requestMatchers("/api/tasks/**").permitAll() // Tasks (public for now)
                        .requestMatchers("/api/tags/**").permitAll() // Tags (public for now)
                        .requestMatchers("/api/task-tags/**").permitAll() // Task-tags (public for now)
                        // Everything else needs authentication
                        .anyRequest().authenticated())

                // Use stateless session - no cookies, only JWT tokens
                // STATELESS = server doesn't store session, each request must have token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Add our JWT filter BEFORE the default UsernamePasswordAuthenticationFilter
                // This means JWT is checked first before any other auth
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Password encoder bean - BCrypt is industry standard
    // BCrypt automatically salts passwords (adds random data before hashing)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication manager bean - needed for auth operations
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

// ============================================
// SECURITY FLOW DIAGRAM
// ============================================
//
// HTTP Request
// |
// v
// +------------------+
// | CorsFilter | <-- Handles CORS (allows frontend to call backend)
// +------------------+
// |
// v
// +------------------+
// | JwtAuthFilter | <-- Our filter (validates JWT token)
// +------------------+
// |
// v
// +------------------+
// | SecurityChain | <-- Checks if endpoint needs auth
// +------------------+
// |
// v
// /api/auth/** → permitAll() → No auth needed
// /api/tasks/** → permitAll() → No auth needed (for now)
// /api/other/** → authenticated() → Must have valid token
// |
// v
// Controller handles request
//
// ============================================
// WHY STATELESS?
// ============================================
//
// Traditional (Stateful):
// - Server stores session data
// - Uses cookies to track users
// - Hard to scale (session must be shared across servers)
//
// JWT (Stateless):
// - Server doesn't store anything
// - Token contains all user info
// - Easy to scale (any server can validate token)
// - Perfect for REST APIs and mobile apps
