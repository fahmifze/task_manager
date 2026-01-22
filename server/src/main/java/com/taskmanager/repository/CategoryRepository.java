package com.taskmanager.repository;

import com.taskmanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this interface as a Spring Data JPA repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // JpaRepository provides: save(), deleteById(), etc.

    // Get all categories for a specific user sorted by creation date
    List<Category> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Search by name for a specific user (case-insensitive)
    List<Category> findByUserIdAndNameContainingIgnoreCase(Long userId, String keyword);
}
