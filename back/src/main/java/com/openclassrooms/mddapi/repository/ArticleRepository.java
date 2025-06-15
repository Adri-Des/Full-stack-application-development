package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Article entities in the database.
 * <p>
 * Extends JpaRepository to provide standard CRUD operations and
 * defines custom query methods for retrieving articles related to subjects.
 * </p>
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {
	
	/**
	 * Retrieves a list of articles associated with the given subject IDs,
	 * ordered by creation date in descending order.
	 *
	 * @param subjectIds the list of subject IDs to filter articles by
	 * @return a list of articles matching the subject IDs, sorted by creation date (newest first)
	 */
    List<Article> findBySubjectIdInOrderByCreatedAtDesc(List<Long> subjectIds);
}