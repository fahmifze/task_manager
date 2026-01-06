package com.taskmanager.dto; // Data Transfer Object for Task 

import java.time.LocalDateTime; // For timestamp fields

// DTO (Data Transfer Object) - Used for API request/response data
// starts of the class dto is hereeeeee
public class TaskDTO {

    // Fields matching Task entity
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
    private Long categoryId; // Category ID for the task
    private String categoryName; // Category name for display
    private String categoryColor; // Category color for display

    public TaskDTO() {} // Required for JSON deserialization, an empty constructor, cannot create object without this

    public TaskDTO(Long id, String title, String description, boolean completed,
                   LocalDateTime createdAt, LocalDateTime updatedAt) { // A constructor with all fields
        
        // Assign parameters to fields
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt; 
        this.dueDate = dueDate;
    }

    // Convert Entity to DTO (for API responses)
    public static TaskDTO fromEntity(com.taskmanager.entity.Task task) { // static method to convert entity to dto, more clean
        TaskDTO dto = new TaskDTO(); // Create new DTO object
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setDueDate(task.getDueDate());
        // Set category info if task has a category
        if (task.getCategory() != null) {
            dto.setCategoryId(task.getCategory().getId());
            dto.setCategoryName(task.getCategory().getName());
            dto.setCategoryColor(task.getCategory().getColor());
        }
        return dto;
    }

    // Convert DTO to Entity (for database operations)
    public com.taskmanager.entity.Task toEntity() { // convert dto to entity so that can save to database
        com.taskmanager.entity.Task task = new com.taskmanager.entity.Task(); // Create new Task entity object
        task.setId(this.id);
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setCompleted(this.completed);
        task.setDueDate(this.dueDate);
        return task;
    }

    // Getters and Setters
    // spring use this to get and set values
    // will to convert JSON -> object and object -> JSON
    //WITHOUT THIS SPRING WILL NOT BE ABLE TO MAP THE FIELDS PROPERLY
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

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getCategoryColor() { return categoryColor; }
    public void setCategoryColor(String categoryColor) { this.categoryColor = categoryColor; }
}

//SOME EXTRA NOTES TO UNDERSTAND DTO
// React sends:     { "title": "Buy milk" }
//                         ↓
// Spring:          new TaskDTO() → setTitle("Buy milk")
//                         ↓
// Service:         taskDTO.toEntity() → Task object
//                         ↓
// Repository:      save(task) → INSERT INTO tasks...
//                         ↓
// Service:         TaskDTO.fromEntity(savedTask)
//                         ↓
// React receives:  { "id": 1, "title": "Buy milk", "completed": false, ... }

