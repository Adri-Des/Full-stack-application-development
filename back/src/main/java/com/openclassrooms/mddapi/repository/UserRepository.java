package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing User entities in the database.
 * <p>
 * Extends JpaRepository to provide standard CRUD operations and
 * defines custom query methods for checking the existence of users and retrieving users by email.
 * </p>
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
     * Checks whether a user exists with the given email address.
     *
     * @param email the email address to check
     * @return {@code true} if a user with the given email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Checks whether a user exists with the given username.
     *
     * @param username the username to check
     * @return {@code true} if a user with the given username exists, {@code false} otherwise
     */
    boolean existsByUsername(String username);
    
    /**
     * Finds a user by email address.
     *
     * @param email the email address of the user to find
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<User> findByEmail(String email);
}