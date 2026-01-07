package com.taskmanager.service;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.Category;
import com.taskmanager.entity.Tag;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.CategoryRepository;
import com.taskmanager.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service // Marks this as a service component for business logic
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    // Constructor injection - Spring auto-injects repositories
    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    // Get all tasks ordered by creation date (newest first)
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAllByOrderByCreatedAtDesc()
                .stream() // Convert list to stream for processing
                .map(TaskDTO::fromEntity) // Convert each Task entity to DTO
                .collect(Collectors.toList()); // Collect back to list
    }

    // Get single task by ID, returns null if not found
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id) // Find by ID but sometimes not found and returns Optional
                .map(TaskDTO::fromEntity) // Convert to DTO if found
                .orElse(null); // Return null if not found
    }

    // Create a new task
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskDTO.toEntity(); // Convert DTO to entity
        // Set category if categoryId is provided
        if (taskDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(taskDTO.getCategoryId()).orElse(null);
            task.setCategory(category);
        }
        // Set tags if tagIds are provided
        if (taskDTO.getTagIds() != null && !taskDTO.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(taskDTO.getTagIds()));
            task.setTags(tags);
        }
        Task savedTask = taskRepository.save(task); // Save to database
        return TaskDTO.fromEntity(savedTask); // Return saved task as DTO
    }

    // Update existing task
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id) //same from getTaskById which find it first using id
                .map(existingTask -> {
                    existingTask.setTitle(taskDTO.getTitle()); // change title
                    existingTask.setDescription(taskDTO.getDescription()); // change description
                    existingTask.setCompleted(taskDTO.isCompleted()); // change status
                    // Update category if categoryId is provided
                    if (taskDTO.getCategoryId() != null) {
                        Category category = categoryRepository.findById(taskDTO.getCategoryId()).orElse(null);
                        existingTask.setCategory(category);
                    } else {
                        existingTask.setCategory(null); // Remove category if not provided
                    }
                    // Update tags if tagIds are provided
                    if (taskDTO.getTagIds() != null && !taskDTO.getTagIds().isEmpty()) {
                        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(taskDTO.getTagIds()));
                        existingTask.setTags(tags);
                    } else {
                        existingTask.setTags(new HashSet<>()); // Clear tags if none provided
                    }
                    Task updatedTask = taskRepository.save(existingTask); // Save changes
                    return TaskDTO.fromEntity(updatedTask);
                })
                .orElse(null); // Return null if task not found
    }

    // Delete task by ID, returns true if deleted
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) { // Check if task exists
            taskRepository.deleteById(id);  // Delete the task
            return true; // Deletion successful
        }
        return false;   // Task not found
    }

    // Toggle task completion status (complete <-> incomplete)
    public TaskDTO toggleTaskCompletion(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setCompleted(!task.isCompleted()); // Flip the status
                    Task updatedTask = taskRepository.save(task); // Save changes
                    return TaskDTO.fromEntity(updatedTask); // Return updated task as DTO
                })
                .orElse(null);
    }

    // Search tasks by title (case-insensitive)
    public List<TaskDTO> searchTasks(String keyword) {
        return taskRepository.findByTitleContainingIgnoreCase(keyword) //search using keyword eg "buy"
                .stream() // Convert list to stream for processing
                .map(TaskDTO::fromEntity) // Convert each Task entity to DTO
                .collect(Collectors.toList()); // Collect back to list
    }

    // Get only incomplete tasks
    public List<TaskDTO> getIncompleteTasks() { // return tasks where completed = false
        return taskRepository.findByCompletedFalseOrderByCreatedAtDesc() // get incomplete tasks
                .stream() // Convert list to stream for processing
                .map(TaskDTO::fromEntity)   // Convert each Task entity to DTO
                .collect(Collectors.toList());  // Collect back to list
    }
}
