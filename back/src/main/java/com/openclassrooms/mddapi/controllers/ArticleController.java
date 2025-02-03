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

@RestController
@RequestMapping("/api/article")
public class ArticleController {
	private final ArticleService articleService;
	private final UserService userService;
	private final CommentService commentService;
	
	private ArticleController(ArticleService articleService, UserService userService, CommentService commentService ){
		this.articleService = articleService;
		this.userService= userService;
		this.commentService = commentService;
	}
	
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
	
	@GetMapping("/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
	    Article article = articleService.getArticleById(id);
	    return ResponseEntity.ok(article);
	}
	
	
	@PostMapping("/{id}/comment")
	public ResponseEntity<Comment> addComment(
	        @PathVariable Long id,
	        @RequestBody String content,
	        @RequestHeader("Authorization") String token) {
		String email = JwtUtils.extractEmail(token.replace("Bearer ", ""));
        Long userId = userService.findUserIdByEmail(email);
	    Comment comment = commentService.addComment(userId, id, content);
	    return ResponseEntity.ok(comment);
	}

	@GetMapping("/{id}/comment")
	public ResponseEntity<List<Comment>> getComments(@PathVariable Long id) {
	    List<Comment> comments = commentService.getCommentsByArticle(id);
	    return ResponseEntity.ok(comments);
	}

}
