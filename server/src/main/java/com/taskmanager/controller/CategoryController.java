package com.taskmanager.controller;

import com.taskmanager.dto.CategoryDTO;
import com.taskmanager.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Handles HTTP requests and returns JSON
@RequestMapping("/api/categories") // Base URL: /api/categories
public class CategoryController {
    
    private final CategoryService categoryService; // Service file that handles business logic and data access

    // Constructor injection - Spring auto-injects CategoryService
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all categories
    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(category); // 200 OK
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    // Create new category
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory); // 201 Created
    }

    // Update existing category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory); // 200 OK
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    // Delete category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}
