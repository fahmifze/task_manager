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

import com.taskmanager.entity.User; // Import User

@Service // Marks this as a service component for business logic
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    // Constructor injection - Spring auto-injects repositories
    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository,
            TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    // Get all tasks for a specific user
    public List<TaskDTO> getAllTasks(User user) {
        return taskRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Get single task by ID for a specific user
    public TaskDTO getTaskById(Long id, User user) {
        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId().equals(user.getId())) // Ensure ownership
                .map(TaskDTO::fromEntity)
                .orElse(null);
    }

    // Create a new task for a specific user
    public TaskDTO createTask(TaskDTO taskDTO, User user) {
        Task task = taskDTO.toEntity();
        task.setUser(user); // Set the owner

        // Set category if categoryId is provided
        if (taskDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(taskDTO.getCategoryId())
                    // Ensure the category also belongs to the user, or is global?
                    // For safely, better to check if category belongs to user.
                    // But for now let's assume valid category ID passed or just simple find.
                    // Ideally: categoryRepository.findByIdAndUserId(...)
                    .orElse(null);

            // Basic check if category belongs to user if found
            if (category != null && !category.getUser().getId().equals(user.getId())) {
                category = null; // Prevent assigning other user's category
            }
            task.setCategory(category);
        }
        // Set tags
        if (taskDTO.getTagIds() != null && !taskDTO.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(taskDTO.getTagIds()));
            task.setTags(tags);
        }
        Task savedTask = taskRepository.save(task);
        return TaskDTO.fromEntity(savedTask);
    }

    // Update existing task for a specific user
    public TaskDTO updateTask(Long id, TaskDTO taskDTO, User user) {
        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId().equals(user.getId())) // Ensure ownership
                .map(existingTask -> {
                    existingTask.setTitle(taskDTO.getTitle());
                    existingTask.setDescription(taskDTO.getDescription());
                    existingTask.setCompleted(taskDTO.isCompleted());

                    if (taskDTO.getCategoryId() != null) {
                        Category category = categoryRepository.findById(taskDTO.getCategoryId()).orElse(null);
                        // Prevent assigning other user's category
                        if (category != null && !category.getUser().getId().equals(user.getId())) {
                            category = null;
                        }
                        existingTask.setCategory(category);
                    } else {
                        existingTask.setCategory(null);
                    }

                    if (taskDTO.getTagIds() != null && !taskDTO.getTagIds().isEmpty()) {
                        Set<Tag> tags = new HashSet<>(tagRepository.findAllById(taskDTO.getTagIds()));
                        existingTask.setTags(tags);
                    } else {
                        existingTask.setTags(new HashSet<>());
                    }
                    Task updatedTask = taskRepository.save(existingTask);
                    return TaskDTO.fromEntity(updatedTask);
                })
                .orElse(null);
    }

    // Delete task by ID for a specific user
    public boolean deleteTask(Long id, User user) {
        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId().equals(user.getId()))
                .map(task -> {
                    taskRepository.delete(task);
                    return true;
                })
                .orElse(false);
    }

    // Toggle task completion status
    public TaskDTO toggleTaskCompletion(Long id, User user) {
        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId().equals(user.getId()))
                .map(task -> {
                    task.setCompleted(!task.isCompleted());
                    Task updatedTask = taskRepository.save(task);
                    return TaskDTO.fromEntity(updatedTask);
                })
                .orElse(null);
    }

    // Search tasks by title for a specific user
    public List<TaskDTO> searchTasks(String keyword, User user) {
        return taskRepository.findByUserIdAndTitleContainingIgnoreCase(user.getId(), keyword)
                .stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Get incomplete tasks for a specific user
    public List<TaskDTO> getIncompleteTasks(User user) {
        return taskRepository.findByUserIdAndCompletedFalseOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
