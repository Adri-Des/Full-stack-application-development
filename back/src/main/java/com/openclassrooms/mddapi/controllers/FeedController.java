package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.UserService;

import antlr.TokenStreamHiddenTokenFilter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {
    private final ArticleService articleService;
    private final UserService userService;

    public FeedController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Article>> getFeed(@RequestHeader("Authorization") String token) {
    	String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        Long userId = userService.findUserIdByEmail(email); 
        List<Article> articles = articleService.getFeed(userId);
        return ResponseEntity.ok(articles);
    }
}