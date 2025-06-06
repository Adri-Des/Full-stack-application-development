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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



/**
 * Controller responsible for handling subscription-related operations
 * such as subscribing to or unsubscribing from a subject, and retrieving user subscriptions.
 */
@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Subscriptions", description = "Endpoints for managing user subscriptions to subjects")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    /**
     * Constructs a SubscriptionController with the given services.
     *
     * @param subscriptionService the service handling subscription logic
     * @param userService         the service handling user logic
     */
    public SubscriptionController(SubscriptionService subscriptionService, UserService userService ) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    
    /**
     * Subscribes the authenticated user to a subject.
     *
     * @param subjectId the ID of the subject to subscribe to
     * @param token     the JWT token from the Authorization header
     * @return a success or error message
     */
    @Operation(summary = "Subscribe to a subject", description = "Allows the authenticated user to subscribe to a specific subject.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscribed successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "User not found"),
    })
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

    /**
     * Unsubscribes the authenticated user from a subject.
     *
     * @param subjectId the ID of the subject to unsubscribe from
     * @param token     the JWT token from the Authorization header
     * @return a success message
     */
    @Operation(summary = "Unsubscribe from a subject", description = "Allows the authenticated user to unsubscribe from a specific subject.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Unsubscribed successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping
    public ResponseEntity<Map<String, String>> unsubscribe(@RequestParam Long subjectId, @RequestHeader("Authorization") String token) {
    	String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        Long userId = userService.findUserIdByEmail(email); 
        subscriptionService.removeSubscription(userId, subjectId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Unsubscribed successfully");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Retrieves the list of subscriptions for the authenticated user.
     *
     * @param token the JWT token from the Authorization header
     * @return a list of the user's subscriptions
     */
    @Operation(summary = "Get user subscriptions", description = "Retrieves all subjects the user is subscribed to.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscriptions retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public List<Subscription> getUserSubscriptions(@RequestHeader("Authorization") String token){
    	String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        Long userId = userService.findUserIdByEmail(email);
        return subscriptionService.getUserSubscriptions(userId);
        
    }


}