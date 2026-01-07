package com.taskmanager.entity;

import jakarta.persistence.*; //DB annotations
import java.time.LocalDateTime; // For timestamp fields

@Entity // Marks this class as a JPA entity/database table
@Table(name = "users") // Maps to "users" table in database
public class User {
    
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generate number by increment
    private Long id; // Unique ID for each user
    @Column(nullable = false, length = 100, unique = true) // Required field (not null), max 100 chars, unique
    private String username; // Username of the user
    @Column(nullable = false, length = 255) // Required field (not null), max 255 chars
    private String password; // Password of the user
    @Column(nullable = false, length = 100, unique = true) // Required field (not null), max 100 chars, unique
    private String email; // Email of the user
    @Column(name = "created_at") // Maps to created_at column
    private LocalDateTime createdAt; // Timestamp when user is created

    @PrePersist // Runs before first save a new user 
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public User() {} // Required by JPA, an empty constructor
    public User(String username, String password, String email) { // Constructor with fields
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
