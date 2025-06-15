package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Repository interface for managing Comment entities in the database.
 * <p>
 * Extends JpaRepository to provide standard CRUD operations and
 * defines custom query methods for retrieving comments related to articles.
 * </p>
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	 /**
     * Retrieves a list of comments associated with the specified article,
     * ordered by creation date in ascending order (oldest first).
     *
     * @param articleId the ID of the article for which to fetch comments
     * @return a list of comments associated with the article, ordered by creation date (ascending)
     */
    List<Comment> findByArticleIdOrderByCreatedAtAsc(Long articleId);
}