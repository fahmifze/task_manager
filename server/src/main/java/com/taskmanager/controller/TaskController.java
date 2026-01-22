package com.taskmanager.controller;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.taskmanager.entity.User;
import com.taskmanager.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController // Handles HTTP requests and returns JSON
@RequestMapping("/api/tasks") // Base URL: /api/tasks
@CrossOrigin(origins = "*") // Allow frontend on different port to call this API
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    // Constructor injection
    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    // Helper to get authenticated user
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks(getCurrentUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO task = taskService.getTaskById(id, getCurrentUser());
        if (task != null) {
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public List<TaskDTO> searchTasks(@RequestParam String keyword) {
        return taskService.searchTasks(keyword, getCurrentUser());
    }

    @GetMapping("/incomplete")
    public List<TaskDTO> getIncompleteTasks() {
        return taskService.getIncompleteTasks(getCurrentUser());
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.createTask(taskDTO, getCurrentUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, taskDTO, getCurrentUser());
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<TaskDTO> toggleTask(@PathVariable Long id) {
        TaskDTO toggledTask = taskService.toggleTaskCompletion(id, getCurrentUser());
        if (toggledTask != null) {
            return ResponseEntity.ok(toggledTask);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id, getCurrentUser());
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

// React App Controller Service
// | | |
// |-- GET /api/tasks --------------->| |
// | |-- getAllTasks() ---------->|
// | |<-- List<TaskDTO> ----------|
// |<-- JSON [task1, task2, ...] -----| |
