package com.openclassrooms.mddapi.services;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;


/**
 * Service responsible for handling user-related operations such as registration,
 * authentication, profile updates.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a UserService with the required UserRepository and PasswordEncoder.
     *
     * @param userRepository  the repository for accessing and managing user data
     * @param passwordEncoder the encoder for encrypting user passwords
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user after validating their email, username, and password.
     *
     * @param user the user to register
     * @return the saved user entity
     * @throws RuntimeException if the email or username is already in use, or the password is invalid
     */
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already in use");
        }
        
        if (!isPasswordValid(user.getPassword())) {
            throw new RuntimeException("Invalid password : The password must be at least 8 characters long with at least 1 number, 1 lowercase letter, 1 uppercase letter, 1 special character ");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    

    /**
     * Validates the strength of a given password using regex.
     *
     * @param password the raw password to validate
     * @return true if the password meets the complexity requirements, false otherwise
     */
    private boolean isPasswordValid(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        // password with at least 1 number, 1 lowercase letter, 1 uppercase letter, 1 special character
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
    
    /**
     * Authenticates a user using their email and raw password.
     *
     * @param email the user's email
     * @param rawPassword the raw password provided
     * @return the authenticated User object
     * @throws RuntimeException if the credentials are invalid
     */
    public User authenticateUser(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword())) 
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }
    
    /**
     * Finds a user by their email.
     *
     * @param email the user's email
     * @return an Optional containing the User if found, otherwise empty
     */
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Retrieves the ID of a user by their email.
     *
     * @param email the user's email
     * @return the user ID
     * @throws RuntimeException if the user is not found
     */
    public Long findUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }
    
    /**
     * Updates the username and/or email of an existing user.
     *
     * @param userId  the ID of the user to update
     * @param updatedUser  a User object containing updated fields
     * @return the updated User object
     * @throws RuntimeException if the user is not found or if the new email/username is already in use
     */
    public User updateUser(Long userId, User updatedUser) {
    	User user = userRepository.findById(userId)
    			 .orElseThrow(() -> new RuntimeException("User not found"));
    	
    	
         
        	if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(updatedUser.getEmail())) {
        	        throw new RuntimeException("Email already in use");
        	   }
        	if (updatedUser.getUsername() != null && !updatedUser.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(updatedUser.getUsername())) {
                throw new RuntimeException("Username already in use");
            }

        	if (updatedUser.getUsername() != null && !updatedUser.getUsername().equals(user.getUsername())) {
        		user.setUsername(updatedUser.getUsername());
        	}
        	if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(user.getEmail())) {
        		user.setEmail(updatedUser.getEmail());
        	}
            return userRepository.save(user);

    }
}