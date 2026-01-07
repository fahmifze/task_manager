package com.taskmanager.config;

import com.taskmanager.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component // Spring will auto-create this filter
public class JwtAuthFilter extends OncePerRequestFilter {
    // OncePerRequestFilter = runs once for every HTTP request

    private final JwtService jwtService;

    // Constructor injection
    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // This method runs for EVERY HTTP request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Step 1: Get Authorization header from request
        // Example header: "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Step 2: Check if header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // No token found, continue to next filter (will be blocked if endpoint needs auth)
            filterChain.doFilter(request, response);
            return;
        }

        // Step 3: Extract JWT token (remove "Bearer " prefix, 7 characters)
        jwt = authHeader.substring(7);

        // Step 4: Extract email/username from token
        userEmail = jwtService.extractUsername(jwt);

        // Step 5: If user found and not already authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Step 6: Validate the token
            if (jwtService.isTokenValid(jwt, userEmail)) {

                // Step 7: Create authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userEmail, // Principal (who is making the request)
                        null, // Credentials (password not needed, we have JWT)
                        new ArrayList<>()); // Authorities/roles (empty for now)

                // Add request details to auth token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Step 8: Set authentication in Security Context
                // Now Spring Security knows this request is authenticated!
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Step 9: Continue to next filter in chain
        filterChain.doFilter(request, response);
    }
}

// ============================================
// JWT FILTER FLOW DIAGRAM
// ============================================
//
// HTTP Request
//      |
//      v
// +------------------+
// | JwtAuthFilter    |
// +------------------+
//      |
//      v
// Has "Authorization: Bearer xxx" header?
//      |
//   No |          Yes
//      v            v
// Continue     Extract token
//      |            |
//      |            v
//      |       Valid token?
//      |            |
//      |         No |          Yes
//      |            v            v
//      |       Continue     Set Authentication
//      |            |            |
//      v            v            v
// +------------------+
// | Next Filter...   |
// | (SecurityConfig) |
// +------------------+
//      |
//      v
// Endpoint (Controller)
