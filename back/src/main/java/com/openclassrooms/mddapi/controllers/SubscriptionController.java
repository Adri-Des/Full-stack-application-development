package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.SubscriptionService;
import com.openclassrooms.mddapi.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    public SubscriptionController(SubscriptionService subscriptionService, UserService userService ) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> subscribe(@RequestParam Long subjectId, @RequestHeader("Authorization") String token) {
    	try {
    		 if (token == null || token.isBlank()) {
    			 Map<String, String> errorTokenResponse = new HashMap<>();
    			 errorTokenResponse.put("error", "Missing Authorization token");
    	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorTokenResponse);
    	        }
    	
    	String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        Long userId = userService.findUserIdByEmail(email); 
        if (userId == null) {
        	Map<String, String> errorUserResponse = new HashMap<>();
        	errorUserResponse.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorUserResponse);
        }
        subscriptionService.addSubscription(userId, subjectId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Subscribed successfully");
        return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
    	Map<String, String> errorRequestResponse = new HashMap<>();
        errorRequestResponse.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(errorRequestResponse);
    } catch (Exception e) {
    	e.printStackTrace();
    	Map<String, String> errorUnexpectedResponse = new HashMap<>();
       errorUnexpectedResponse.put("error", "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorUnexpectedResponse);
    }
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> unsubscribe(@RequestParam Long subjectId, @RequestHeader("Authorization") String token) {
    	String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        Long userId = userService.findUserIdByEmail(email); 
        subscriptionService.removeSubscription(userId, subjectId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Unsubscribed successfully");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public List<Subscription> getUserSubscriptions(@RequestHeader("Authorization") String token){
    	String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        Long userId = userService.findUserIdByEmail(email);
        return subscriptionService.getUserSubscriptions(userId);
        
    }


}