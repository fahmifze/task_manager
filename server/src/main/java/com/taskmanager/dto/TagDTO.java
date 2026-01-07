package com.taskmanager.dto; // Data Transfer Object for Task 

import java.time.LocalDateTime; // For timestamp fields

// DTO (Data Transfer Object) - Used for API request/response data
// starts of the class dto is hereeeeee
public class TagDTO {
    private Long id; // Unique ID for each tag
    private String name; // Tag name
    private LocalDateTime createdAt; // Timestamp when tag was created

    public TagDTO() {} // Required for JSON deserialization, an empty constructor
    public TagDTO(Long id, String name, LocalDateTime createdAt) { // A constructor with all fields
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }
    // Convert Entity to DTO (for API responses)
    public static TagDTO fromEntity(com.taskmanager.entity.Tag tag) { // static method to convert entity to dto, more clean
        TagDTO dto = new TagDTO(); // Create new DTO object
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setCreatedAt(tag.getCreatedAt());
        return dto;
    }

    // Convert DTO to Entity (for database operations)
    public com.taskmanager.entity.Tag toEntity() { // convert dto to entity so that can save to database
        com.taskmanager.entity.Tag tag = new com.taskmanager.entity.Tag(); // Create new Tag entity object
        tag.setId(this.id);
        tag.setName(this.name);
        return tag;
    }

    // Getters and Setters
    public Long getId() { return id; } // get unique ID
    public void setId(Long id) { this.id = id; } // set unique ID
    public String getName() { return name; } // get name
    public void setName(String name) { this.name = name; } // set name
    public LocalDateTime getCreatedAt() { return createdAt; } // get createdAt
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; } // set createdAt
}
