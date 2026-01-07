package com.taskmanager.repository;

import com.taskmanager.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this as a data repository component
public interface TagRepository extends JpaRepository<Tag, Long> {
    // This class will be used to interact with the database for Tag entities
    List<Tag> findByNameContainingIgnoreCase(String Name); // Search tasks by tag name (case-insensitive)
    List<Tag> findAllByOrderByCreatedAtDesc(); // Get all tasks sorted by date (newest first)

    
}
    