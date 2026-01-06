package com.taskmanager.repository;

import com.taskmanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this as a data repository component
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // JpaRepository provides: findAll(), findById(), save(), deleteById(), etc.
    
    List<Category> findByNameContainingIgnoreCase(String keyword); // Search by name (case-insensitive)
    List<Category> findAllByOrderByCreatedAtDesc(); // Get all categories sorted by date (newest first)

}
