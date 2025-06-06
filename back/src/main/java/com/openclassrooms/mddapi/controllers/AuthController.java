package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.User;

import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller responsible for handling authentication-related endpoints,
 * such as user registration, login, profile retrieval, and profile updates.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration, login, and profile management")
public class AuthController {
    private final UserService userService;

    /**
     * Constructs a new AuthController with the provided UserService.
     *
     * @param userService the user service to use for operations
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user in the system.
     *
     * @param user the user to register
     * @return a success message or error if registration fails
     */
    @Operation(summary = "Register a new user", description = "Registers a new user using the provided user details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Registration failed ")
    })
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
    	try {
            userService.registerUser(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param user the user credentials
     * @return JWT token if authentication is successful
     */
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
        String token = JwtUtils.generateToken(authenticatedUser.getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Retrieves the authenticated user's profile.
     *
     * @param token the JWT token from the Authorization header
     * @return the user profile
     */
    @Operation(summary = "Get current user profile", description = "Retrieves the profile of the currently authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/me")
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String token) {
        String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }
    
    /**
     * Updates the profile of the currently authenticated user.
     *
     * @param token        the JWT token from the Authorization header
     * @param updatedUser  the updated user details
     * @return the updated user or an error message
     */
    @Operation(summary = "Update user profile", description = "Updates the profile information of the currently authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid update data")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateUserProfile(@RequestHeader("Authorization") String token, @RequestBody User updatedUser) {
        try {
            String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
            /*User user = userService.findUserByEmail(email)
            		.orElseThrow(() -> new RuntimeException("User not found"));;*/
            Long userId = userService.findUserIdByEmail(email);
            
            
           /* user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());*/

           User user = userService.updateUser(userId,updatedUser);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
        	return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}