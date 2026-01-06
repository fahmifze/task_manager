package com.taskmanager.service;

import com.taskmanager.dto.CategoryDTO;
import com.taskmanager.entity.Category;
import com.taskmanager.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Marks this as a service component for business logic

public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    // Constructor injection - Spring auto-injects CategoryRepository
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get all categories ordered by creation date (newest first)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAllByOrderByCreatedAtDesc()
                .stream() // Convert list to stream for processing
                .map(CategoryDTO::fromEntity) // Convert each Category entity to DTO
                .collect(Collectors.toList()); // Collect back to list
    }

    // Get single category by ID, returns null if not found
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id) // Find by ID but sometimes not found and returns Optional
                .map(CategoryDTO::fromEntity) // Convert to DTO if found
                .orElse(null); // Return null if not found
}
    // Create a new category
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryDTO.toEntity(); // Convert DTO to entity
        Category savedCategory = categoryRepository.save(category); // Save to database
        return CategoryDTO.fromEntity(savedCategory); // Return saved category as DTO
    }
    // Update existing category
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id) //same from getCategoryById which find it first using id
                .map(existingCategory -> {
                    existingCategory.setName(categoryDTO.getName()); // change name
                    Category updatedCategory = categoryRepository.save(existingCategory); // Save changes
                    return CategoryDTO.fromEntity(updatedCategory);
                })
                .orElse(null); // Return null if category not found
    }
    // Delete category by ID, returns true if deleted
    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) { // Check if category exists
            categoryRepository.deleteById(id);  // Delete the category
            return true; // Deletion successful
        }
        return false; // Category not found
    }
}
