package com.taskmanager.repository;

import com.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this interface as a Spring Data JPA repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // JpaRepository provides: findAll(), findById(), save(), deleteById(), etc.

    // Get all tasks for a specific user sorted by date (newest first)
    List<Task> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Search tasks by title for a specific user (case-insensitive)
    List<Task> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);

    // Get incomplete tasks for a specific user sorted by date
    List<Task> findByUserIdAndCompletedFalseOrderByCreatedAtDesc(Long userId);
}
