package com.taskmanager.service;

import com.taskmanager.dto.CategoryDTO;
import com.taskmanager.entity.Category;
import com.taskmanager.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.taskmanager.entity.User; // Import User

@Service // Marks this as a service component for business logic
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Constructor injection - Spring auto-injects CategoryRepository
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get all categories for a specific user
    public List<CategoryDTO> getAllCategories(User user) {
        return categoryRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Get single category by ID for a specific user
    public CategoryDTO getCategoryById(Long id, User user) {
        return categoryRepository.findById(id)
                .filter(category -> category.getUser().getId().equals(user.getId())) // Ensure ownership
                .map(CategoryDTO::fromEntity)
                .orElse(null);
    }

    // Create a new category for a specific user
    public CategoryDTO createCategory(CategoryDTO categoryDTO, User user) {
        Category category = categoryDTO.toEntity();
        category.setUser(user); // Set owner
        Category savedCategory = categoryRepository.save(category);
        return CategoryDTO.fromEntity(savedCategory);
    }

    // Update existing category for a specific user
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO, User user) {
        return categoryRepository.findById(id)
                .filter(category -> category.getUser().getId().equals(user.getId())) // Ensure ownership
                .map(existingCategory -> {
                    existingCategory.setName(categoryDTO.getName());
                    existingCategory.setColor(categoryDTO.getColor());
                    existingCategory.setDescription(categoryDTO.getDescription());
                    Category updatedCategory = categoryRepository.save(existingCategory);
                    return CategoryDTO.fromEntity(updatedCategory);
                })
                .orElse(null);
    }

    // Delete category by ID for a specific user
    public boolean deleteCategory(Long id, User user) {
        return categoryRepository.findById(id)
                .filter(category -> category.getUser().getId().equals(user.getId())) // Ensure ownership
                .map(category -> {
                    categoryRepository.delete(category);
                    return true;
                })
                .orElse(false);
    }
}
