package com.taskmanager.entity;
import jakarta.persistence.*; //DB annotations
import java.time.LocalDateTime; // For timestamp fields

@Entity // Marks this class as a JPA entity/database table
@Table(name = "tags") // Maps to "tags" table in database

public class Tag {
    
    @Id // NEXT FIELD Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generate number by increment
    private Long id; // Unique ID for each tag

    @Column(nullable = false, length = 50) // Required field (not null), max 50 chars
    private String name; // Tag name

    @Column(name = "created_at") // Maps to created_at column
    private LocalDateTime createdAt; 

    @PrePersist // Runs before first save a new tag 
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

    }


    public Tag() {} // Required by JPA, an empty constructor

    public Tag(String name) { //A constructor with name
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; } //get unique ID
    public void setId(Long id) { this.id = id; } // set unique ID

    public String getName() { return name; } // get name
    public void setName(String name) { this.name = name; } // set name

    public LocalDateTime getCreatedAt() { return createdAt; } // get createdAt

}
