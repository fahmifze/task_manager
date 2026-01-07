package com.taskmanager.dto;

// DTO (Data Transfer Object) for authentication requests
// Used for both register and login endpoints
// This is what the frontend sends to the backend
public class AuthRequestDTO {

    private String username; // Username for registration (not needed for login)
    private String email; // Email address - used as unique identifier
    private String password; // Plain text password (will be hashed in AuthService)

    // Empty constructor - required for JSON deserialization
    public AuthRequestDTO() {}

    // Constructor with all fields
    public AuthRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

// ============================================
// USAGE EXAMPLE
// ============================================
//
// Register request JSON:
// {
//   "username": "john",
//   "email": "john@example.com",
//   "password": "mypassword123"
// }
//
// Login request JSON:
// {
//   "email": "john@example.com",
//   "password": "mypassword123"
// }
