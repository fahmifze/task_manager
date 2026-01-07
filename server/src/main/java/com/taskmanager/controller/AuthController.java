package com.taskmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.dto.AuthRequestDTO;
import com.taskmanager.dto.AuthResponseDTO;
import com.taskmanager.service.AuthService;

@RestController // Handles HTTP requests and returns JSON
@RequestMapping("/api/auth") // Base URL for all auth endpoints
@CrossOrigin(origins = "*") // Allow all origins (for development)
public class AuthController {

    // Inject AuthService to handle business logic
    private final AuthService authService;

    // Constructor injection - Spring will auto inject AuthService
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register endpoint - creates new user account
    // POST /api/auth/register
    // Request body: { username, email, password }
    // Returns: { token, username, email, message } with 201 Created
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO request) {
        try {
            // Call service to register user, return 201 Created on success
            return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Return 400 Bad Request if registration fails (duplicate email/username)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Login endpoint - authenticates user and returns JWT token
    // POST /api/auth/login
    // Request body: { email, password }
    // Returns: { token, username, email, message } with 200 OK
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        try {
            // Call service to login user, return 200 OK on success
            return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Return 400 Bad Request if login fails (wrong password, user not found)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

// ============================================
// AUTH FLOW DIAGRAM
// ============================================
//
// REGISTER:
// Client (React)    AuthController    AuthService    UserRepository    Database
//      |                 |                |                |              |
//      |-- POST /register -->             |                |              |
//      |                 |-- register() -->               |              |
//      |                 |                |-- existsByEmail() ----------->|
//      |                 |                |<-- true/false ----------------|
//      |                 |                |-- save(user) ---------------->|
//      |                 |                |<-- saved user ----------------|
//      |                 |<-- AuthResponseDTO (with token)                |
//      |<-- 201 Created --|                |                |              |
//
// LOGIN:
// Client (React)    AuthController    AuthService    UserRepository    Database
//      |                 |                |                |              |
//      |-- POST /login -->                |                |              |
//      |                 |-- login() ---->                |              |
//      |                 |                |-- findByEmail() ------------->|
//      |                 |                |<-- user ----------------------|
//      |                 |                |-- check password              |
//      |                 |                |-- generate JWT token          |
//      |                 |<-- AuthResponseDTO (with token)                |
//      |<-- 200 OK ------|                |                |              |
