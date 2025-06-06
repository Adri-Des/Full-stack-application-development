package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Service responsible for managing comments on articles.
 */
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a CommentService with the required repositories.
     *
     * @param commentRepository the repository for managing comments
     * @param articleRepository the repository for retrieving articles
     * @param userRepository    the repository for retrieving users
     */
    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    /**
     * Adds a new comment to an article.
     *
     * @param userId    the ID of the user posting the comment
     * @param articleId the ID of the article to comment on
     * @param content   the content of the comment
     * @return the saved Comment instance
     * @throws RuntimeException if the user or article is not found
     */
    public Comment addComment(Long userId, Long articleId, String content) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setArticle(article);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    
    /**
     * Retrieves all comments for a given article, sorted by creation time in ascending order.
     *
     * @param articleId the ID of the article
     * @return a list of comments associated with the article
     */
    public List<Comment> getCommentsByArticle(Long articleId) {
        return commentRepository.findByArticleIdOrderByCreatedAtAsc(articleId);
    }
}