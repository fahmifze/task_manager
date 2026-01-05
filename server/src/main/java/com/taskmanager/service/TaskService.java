package com.taskmanager.service;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.entity.Task;
import com.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Marks this as a service component for business logic
public class TaskService {

    private final TaskRepository taskRepository;

    // Constructor injection - Spring auto-injects TaskRepository
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
        return taskRepository.findById(id)
                .map(TaskDTO::fromEntity) // Convert to DTO if found
                .orElse(null); // Return null if not found
    }

    // Create a new task
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskDTO.toEntity(); // Convert DTO to entity
        Task savedTask = taskRepository.save(task); // Save to database
        return TaskDTO.fromEntity(savedTask); // Return saved task as DTO
    }

    // Update existing task
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setTitle(taskDTO.getTitle()); // Update title
                    existingTask.setDescription(taskDTO.getDescription()); // Update description
                    existingTask.setCompleted(taskDTO.isCompleted()); // Update status
                    Task updatedTask = taskRepository.save(existingTask); // Save changes
                    return TaskDTO.fromEntity(updatedTask);
                })
                .orElse(null); // Return null if task not found
    }

    // Delete task by ID, returns true if deleted
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Toggle task completion status (complete <-> incomplete)
    public TaskDTO toggleTaskCompletion(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setCompleted(!task.isCompleted()); // Flip the status
                    Task updatedTask = taskRepository.save(task);
                    return TaskDTO.fromEntity(updatedTask);
                })
                .orElse(null);
    }

    // Search tasks by title (case-insensitive)
    public List<TaskDTO> searchTasks(String keyword) {
        return taskRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Get only incomplete tasks
    public List<TaskDTO> getIncompleteTasks() {
        return taskRepository.findByCompletedFalseOrderByCreatedAtDesc()
                .stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
