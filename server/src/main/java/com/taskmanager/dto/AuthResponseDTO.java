package com.taskmanager.dto;

// DTO (Data Transfer Object) for authentication responses
// This is what the backend sends back to the frontend after login/register
public class AuthResponseDTO {
    private String token; // JWT token - frontend stores this and sends with every request
    private String username; // Username of the logged in user
    private String email; // Email of the logged in user
    private String message; // Status message: "registered" or "login"

    // Empty constructor - required for JSON serialization
    public AuthResponseDTO() {}

    // Constructor with all fields
    public AuthResponseDTO(String token, String username, String email, String message) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.message = message;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

// ============================================
// RESPONSE EXAMPLE
// ============================================
//
// Success response JSON:
// {
//   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
//   "username": "john",
//   "email": "john@example.com",
//   "message": "registered"
// }
//
// Frontend stores token in localStorage:
// localStorage.setItem("token", response.token);
//
// Frontend sends token with every request:
// headers: { "Authorization": "Bearer " + token }
