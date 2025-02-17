package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

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
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
        String token = JwtUtils.generateToken(authenticatedUser.getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/me")
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String token) {
        String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }
    
    
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