package com.taskmanager.dto;

import java.time.LocalDateTime; // For timestamp fields
// DTO (Data Transfer Object) - Used for API request/response data

public class CategoryDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }
    // Convert Entity to DTO
    public static CategoryDTO fromEntity(com.taskmanager.entity.Category category) { 
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setCreatedAt(category.getCreatedAt());
        return dto;
    }
    // Convert DTO to Entity
    public com.taskmanager.entity.Category toEntity() {     
        com.taskmanager.entity.Category category = new com.taskmanager.entity.Category();
        category.setId(this.id);
        category.setName(this.name);
        return category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
