package com.taskmanager.repository;

import com.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this as a data repository component
public interface TaskRepository extends JpaRepository<Task, Long> {
    // JpaRepository provides: findAll(), findById(), save(), deleteById(), etc.

    List<Task> findByCompleted(boolean completed); // Find tasks by completion status

    List<Task> findByTitleContainingIgnoreCase(String keyword); // Search by title (case-insensitive)

    List<Task> findAllByOrderByCreatedAtDesc(); // Get all tasks sorted by date (newest first)

    List<Task> findByCompletedFalseOrderByCreatedAtDesc(); // Get incomplete tasks sorted by date
}
