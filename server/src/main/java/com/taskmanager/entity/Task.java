package com.taskmanager.entity;

import jakarta.persistence.*; //DB annotations
import java.time.LocalDateTime; // For timestamp fields

@Entity // Marks this class as a JPA entity/database table
@Table(name = "tasks") // Maps to "tasks" table in database
public class Task {

    @Id // NEXT FIELD Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generate number by increment
    private Long id; // Unique ID for each task

    @Column(nullable = false, length = 255) // Required field (not null), max 255 chars
    private String title; // Task title

    @Column(columnDefinition = "TEXT") // can hold large text
    private String description; // Task details

    @Column(nullable = false) // Required field (not null)
    private boolean completed = false; // Default: not completed

    @Column(name = "created_at") // Maps to created_at column
    private LocalDateTime createdAt; 

    @Column(name = "updated_at") // Maps to updated_at column
    private LocalDateTime updatedAt; //store last date time

     @Column(name = "due_date") // Maps to due_date column
    private LocalDateTime dueDate; //store due date time

    @PrePersist // Runs before first save a new task 
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate // Runs before each update to an existing task
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Task() {} // Required by JPA, an empty constructor

    public Task(String title, String description) { //A constructor with title and description
        this.title = title;
        this.description = description;
        this.completed = false;
    }

    // Getters and Setters
    public Long getId() { return id; } //get unique ID
    public void setId(Long id) { this.id = id; } // set unique ID

    public String getTitle() { return title; } // get title
    public void setTitle(String title) { this.title = title; } // set title

    public String getDescription() { return description; } //get description   
    public void setDescription(String description) { this.description = description; } // set description

    public boolean isCompleted() { return completed; } // get completed status
    public void setCompleted(boolean completed) { this.completed = completed; } // set completed status

    public LocalDateTime getCreatedAt() { return createdAt; } // get created at timestamp
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; } // set created at timestamp

    public LocalDateTime getUpdatedAt() { return updatedAt; } // get updated at timestamp
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; } // set updated at timestamp

    public LocalDateTime getDueDate() { return dueDate; } // get due date timestamp
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; } // set due date timestamp
}
