package com.taskmanager.entity;

import jakarta.persistence.*; //DB annotations
import java.time.LocalDateTime; // For timestamp fields

@Entity // Marks this class as a JPA entity/database table
@Table(name = "categories") // Maps
public class Category {
    
    @Id // NEXT FIELD Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generate number by increment
    private Long id; // Unique ID for each category

    @Column(nullable = false, length = 100) // Required field (not null), max 100 chars
    private String name; // Category name

    @Column(name = "created_at") // Maps to created_at column
    private LocalDateTime createdAt;

    @PrePersist // Runs before first save a new category 
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Category() {} // Required by JPA, an empty constructor

    public Category(String name) { //A constructor with name
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; } //get unique ID
    public void setId(Long id) { this.id = id; } // set unique ID

    public String getName() { return name; } // get name
    public void setName(String name) { this.name = name; } // set name

    public LocalDateTime getCreatedAt() { return createdAt; } // get createdAt
}
