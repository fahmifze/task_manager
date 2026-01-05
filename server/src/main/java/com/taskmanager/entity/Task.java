package com.taskmanager.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // Marks this class as a JPA entity (database table)
@Table(name = "tasks") // Maps to "tasks" table in database
public class Task {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long id;

    @Column(nullable = false, length = 255) // Required field, max 255 chars
    private String title;

    @Column(columnDefinition = "TEXT") // Long text field
    private String description;

    @Column(nullable = false)
    private boolean completed = false; // Default: not completed

    @Column(name = "created_at") // Maps to created_at column
    private LocalDateTime createdAt;

    @Column(name = "updated_at") // Maps to updated_at column
    private LocalDateTime updatedAt;

    @PrePersist // Runs before first save
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate // Runs before each update
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Task() {} // Required by JPA

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
