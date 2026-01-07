package com.taskmanager.repository;

import com.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // For Optional return type, better for null safety and auth handling

@Repository // Marks this as a data repository component

public interface UserRepository extends JpaRepository<User, Long> {
    // This class will be used to interact with the database for User entities
    Optional<User> findByUsername(String username); // Find user by username
    Optional<User> findByEmail(String email); // Find user by email

    boolean existsByUsername(String username); // Check if username exists
    boolean existsByEmail(String email); // Check if email exists
}
