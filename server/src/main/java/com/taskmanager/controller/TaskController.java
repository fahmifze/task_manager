package com.taskmanager.controller;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Handles HTTP requests and returns JSON
@RequestMapping("/api/tasks") // Base URL: /api/tasks
@CrossOrigin(origins = "*") // Allow frontend on different port to call this API
public class TaskController {

    private final TaskService taskService; // Service file that handles business logic and data access

    // Constructor injection - Spring auto-injects TaskService
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // in this method it will add the endpoints for CRUD operations
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    // in this method it will get task by specified id only and return 404 code if not found
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO task = taskService.getTaskById(id);
        if (task != null) {
            return ResponseEntity.ok(task); // 200 OK
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    // in this method it will search tasks by title keyword for example "buy"
    @GetMapping("/search")
    public List<TaskDTO> searchTasks(@RequestParam String keyword) {
        return taskService.searchTasks(keyword);
    }

    // for this method pulak it will get only incomplete tasks and return as list
    @GetMapping("/incomplete")
    public List<TaskDTO> getIncompleteTasks() {
        return taskService.getIncompleteTasks();
    }

    // for this method it will create new task and return 201 code as for meaning the task has been created
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) { // RequestBody to get JSON data from request and convert to TaskDTO
        TaskDTO createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask); // 201 Created
    }

    // in this method it will update existing task by id, return 200 if okay la and return 404 code if problem
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, taskDTO);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask); // 200 OK
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    // PUT /api/tasks/{id}/toggle - Toggle task completion status
    @PutMapping("/{id}/toggle") // Endpoint to toggle completion status
    public ResponseEntity<TaskDTO> toggleTask(@PathVariable Long id) { // Get task ID from URL path
        TaskDTO toggledTask = taskService.toggleTaskCompletion(id); // Toggle the completion status for example from true to false or false to true
        if (toggledTask != null) {
            return ResponseEntity.ok(toggledTask); // 200 OK
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    // in this last method in this fie it will delete task by id and return 204 code if deleted successfully or 404 code if not found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) { // Get task ID from URL path
        boolean deleted = taskService.deleteTask(id); // Delete the task
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}

// React App                          Controller                    Service
//     |                                  |                            |
//     |-- GET /api/tasks --------------->|                            |
//     |                                  |-- getAllTasks() ---------->|
//     |                                  |<-- List<TaskDTO> ----------|
//     |<-- JSON [task1, task2, ...] -----|                            |

