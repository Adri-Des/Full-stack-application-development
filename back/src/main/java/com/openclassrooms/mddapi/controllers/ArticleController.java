package com.openclassrooms.mddapi.controllers;

import java.util.List;


import java.util.Map;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.UserService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


/**
 * REST controller for managing articles and associated comments.
 * Provides endpoints for creating articles, retrieving articles by ID,
 * and handling comments related to articles.
 */
@RestController
@RequestMapping("/api/article")
@Tag(name = "Articles", description = "Endpoints for managing articles and comments")
public class ArticleController {
	private final ArticleService articleService;
	private final UserService userService;
	private final CommentService commentService;
	
	 /**
     * Constructor to inject services for article, user, and comment operations.
     *
     * @param articleService  the service handling article-related logic
     * @param userService     the service for accessing user data
     * @param commentService  the service for managing comments
     */
	private ArticleController(ArticleService articleService, UserService userService, CommentService commentService ){
		this.articleService = articleService;
		this.userService= userService;
		this.commentService = commentService;
	}
	
	/**
     * Creates a new article with the provided data.
     *
     * @param payload a map containing the title, content, and subjectId of the article
     * @param token   the JWT token used to identify the authenticated user
     * @return the created Article entity
     */
	
	@Operation(summary = "Create a new article")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Article published successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
	@PostMapping
	public ResponseEntity<Article> createArticle(
	        @RequestBody Map<String, String> payload,
	        @RequestHeader("Authorization") String token) {
		String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        Long userId = userService.findUserIdByEmail(email); 

	    String title = payload.get("title");
	    String content = payload.get("content");
	    Long subjectId = Long.valueOf(payload.get("subjectId"));

	    Article article = articleService.createArticle(userId, subjectId, title, content);
	    return ResponseEntity.ok(article);
	}
	
	 /**
     * Retrieves a specific article by its ID.
     *
     * @param id the ID of the article
     * @return the Article entity corresponding to the provided ID
     */
	
	@Operation(summary = "Get an article by its ID")
    @ApiResponse(responseCode = "200", description = "Article found")
	@GetMapping("/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
	    Article article = articleService.getArticleById(id);
	    return ResponseEntity.ok(article);
	}
	
	 /**
     * Adds a new comment to the specified article.
     *
     * @param id      the ID of the article to comment on
     * @param content the content of the comment
     * @param token   the JWT token used to identify the authenticated user
     * @return the created Comment entity
     */
	
	 @Operation(summary = "Add a comment to an article")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Comment added successfully"),
	        @ApiResponse(responseCode = "401", description = "Unauthorized")
	    })
	@PostMapping("/{id}/comment")
	public ResponseEntity<Comment> addComment(
	        @PathVariable Long id,
	        @RequestBody  String content,
	        @RequestHeader("Authorization") String token) {
		String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        Long userId = userService.findUserIdByEmail(email);
	    Comment comment = commentService.addComment(userId, id, content);
	    System.out.println(content);
	    return ResponseEntity.ok(comment);
	}

	 /**
     * Retrieves all comments associated with a specific article.
     *
     * @param id the ID of the article
     * @return a list of comments for the article
     */
	 
	 @Operation(summary = "Get comments for a specific article")
	    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully")
	@GetMapping("/{id}/comment")
	public ResponseEntity<List<Comment>> getComments(@PathVariable Long id) {
	    List<Comment> comments = commentService.getCommentsByArticle(id);
	    return ResponseEntity.ok(comments);
	}

}
