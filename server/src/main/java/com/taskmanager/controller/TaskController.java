package com.taskmanager.controller;

import com.taskmanager.dto.TaskDTO;
import com.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * CONTROLLER = The "waiter" of your application
 * It receives requests from the frontend (React), talks to the Service layer,
 * and sends back responses. It doesn't do any business logic itself.
 *
 * Think of it like a restaurant:
 * - Controller = Waiter (takes orders, delivers food)
 * - Service = Kitchen (prepares the food)
 * - Repository = Pantry (stores ingredients)
 */

@RestController // Tells Spring: "This class handles HTTP requests and returns JSON"
@RequestMapping("/api/tasks") // All URLs in this controller start with /api/tasks
@CrossOrigin(origins = "*") // Allows frontend (React) on different port to call this API
public class TaskController {

    // This is called "Dependency Injection" - Spring creates TaskService and gives it to us
    // We don't do: new TaskService() - Spring handles object creation for us
    private final TaskService taskService;

    // Constructor - Spring automatically passes TaskService when creating this controller
    // This is called "Constructor Injection" - the recommended way to get dependencies
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /*
     * GET /api/tasks
     * Returns: List of all tasks as JSON
     * Example response: [{"id": 1, "title": "Buy milk", "completed": false}, ...]
     *
     * @GetMapping = Handle HTTP GET requests (used for READING data)
     */
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    /*
     * GET /api/tasks/5
     * Returns: Single task with that ID
     *
     * @PathVariable = Gets the {id} value from the URL
     * Example: /api/tasks/5 -> id = 5
     *
     * ResponseEntity = Lets us control the HTTP status code (200, 404, etc.)
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO task = taskService.getTaskById(id);
        if (task != null) {
            return ResponseEntity.ok(task); // 200 OK - Task found, here it is!
        }
        return ResponseEntity.notFound().build(); // 404 Not Found - Task doesn't exist
    }

    /*
     * GET /api/tasks/search?keyword=buy
     * Returns: Tasks that contain "buy" in the title
     *
     * @RequestParam = Gets value from URL query string (after the ?)
     * Example: /api/tasks/search?keyword=buy -> keyword = "buy"
     */
    @GetMapping("/search")
    public List<TaskDTO> searchTasks(@RequestParam String keyword) {
        return taskService.searchTasks(keyword);
    }

    /*
     * GET /api/tasks/incomplete
     * Returns: Only tasks where completed = false
     */
    @GetMapping("/incomplete")
    public List<TaskDTO> getIncompleteTasks() {
        return taskService.getIncompleteTasks();
    }

    /*
     * POST /api/tasks
     * Creates a new task
     *
     * @PostMapping = Handle HTTP POST requests (used for CREATING data)
     * @RequestBody = Takes the JSON from request body and converts it to TaskDTO object
     *
     * Frontend sends: {"title": "Buy milk", "description": "2% milk"}
     * Spring automatically converts this JSON into a TaskDTO object for us!
     *
     * Returns 201 Created (not 200 OK) because we created something new
     */
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    /*
     * PUT /api/tasks/5
     * Updates an existing task
     *
     * @PutMapping = Handle HTTP PUT requests (used for UPDATING data)
     *
     * Combines @PathVariable (get ID from URL) and @RequestBody (get data from request)
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, taskDTO);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask); // 200 OK - Updated successfully
        }
        return ResponseEntity.notFound().build(); // 404 - Can't update what doesn't exist
    }

    /*
     * PUT /api/tasks/5/toggle
     * Toggles task completion: if completed -> incomplete, if incomplete -> completed
     *
     * This is a custom endpoint - not standard REST, but very convenient!
     */
    @PutMapping("/{id}/toggle")
    public ResponseEntity<TaskDTO> toggleTask(@PathVariable Long id) {
        TaskDTO toggledTask = taskService.toggleTaskCompletion(id);
        if (toggledTask != null) {
            return ResponseEntity.ok(toggledTask);
        }
        return ResponseEntity.notFound().build();
    }

    /*
     * DELETE /api/tasks/5
     * Deletes a task
     *
     * @DeleteMapping = Handle HTTP DELETE requests (used for DELETING data)
     *
     * Returns 204 No Content (success, but nothing to return)
     * ResponseEntity<Void> = Response with no body (Void = nothing)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 - Deleted, nothing to return
        }
        return ResponseEntity.notFound().build(); // 404 - Can't delete what doesn't exist
    }
}
