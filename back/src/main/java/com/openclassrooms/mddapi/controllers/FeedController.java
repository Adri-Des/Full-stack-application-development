package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Article;

import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.UserService;

import antlr.TokenStreamHiddenTokenFilter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller responsible for providing the user's article feed.
 */
@RestController
@RequestMapping("/api/feed")
@Tag(name = "Feed", description = "Endpoints for retrieving the user's article feed")
public class FeedController {
    private final ArticleService articleService;
    private final UserService userService;

    /**
     * Constructs a new FeedController with the given services.
     *
     * @param articleService the article service to retrieve feed content
     * @param userService    the user service to identify the current user
     */
    public FeedController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    /**
     * Retrieves the personalized feed for the currently authenticated user.
     *
     * @param token the JWT token from the Authorization header
     * @return a list of articles relevant to the user
     */
    @Operation(summary = "Get user feed", description = "Retrieves a list of articles based on the user's subscriptions.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feed retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized or invalid token")
    })
    @GetMapping
    public ResponseEntity<List<Article>> getFeed(@RequestHeader("Authorization") String token) {
    	String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        Long userId = userService.findUserIdByEmail(email); 
        List<Article> articles = articleService.getFeed(userId);
        return ResponseEntity.ok(articles);
    }
}