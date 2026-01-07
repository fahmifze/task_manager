package com.taskmanager.controller;

import com.taskmanager.dto.TagDTO;
import com.taskmanager.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Handles HTTP requests and returns JSON
@RequestMapping("/api/tags") // Base URL: /api/tags
    
public class TagController {
 
    private final TagService tagService; // Service file that handles business logic and data access
        // Constructor injection - Spring auto-injects TagService
        public TagController(TagService tagService) {
            this.tagService = tagService;
        }

        // Get all tags
        @GetMapping
        public List<TagDTO> getAllTags() {
            return tagService.getAllTags();
        }

        // Get tag by ID
        @GetMapping("/{id}")
        public ResponseEntity<TagDTO> getTagById(@PathVariable Long id) {
            TagDTO tag = tagService.getTagById(id);
            if (tag != null) {
                return ResponseEntity.ok(tag); // 200 OK
            }
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        // Create new tag
        @PostMapping
        public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tagDTO) {
            TagDTO createdTag = tagService.createTag(tagDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTag); // 201 Created
        }

        // Update existing tag
        @PutMapping("/{id}")
        public ResponseEntity<TagDTO> updateTag(@PathVariable Long id, @RequestBody TagDTO tagDTO) {
            TagDTO updatedTag = tagService.updateTag(id, tagDTO);
            if (updatedTag != null) {
                return ResponseEntity.ok(updatedTag); // 200 OK
            }
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        // Delete tag by ID
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
            boolean deleted = tagService.deleteTag(id);
            if (deleted) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    
}
