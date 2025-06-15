package com.openclassrooms.mddapi.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Subscription;

@Repository
/**
 * Repository interface for managing Subscription entities in the database.
 * <p>
 * Extends JpaRepository to provide standard CRUD operations and
 * defines custom query methods for retrieving and checking user subscriptions to subjects.
 * </p>
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	
	/**
     * Finds a subscription by user ID and subject ID.
     *
     * @param userId the ID of the user
     * @param subjectId the ID of the subject
     * @return an Optional containing the subscription if found, or empty if not found
     */
	Optional<Subscription> findByUserIdAndSubjectId(Long userId, Long subjectId);
	
	/**
     * Checks whether a subscription exists for a given user ID and subject ID.
     *
     * @param userId the ID of the user
     * @param subjectId the ID of the subject
     * @return {@code true} if a subscription exists, {@code false} otherwise
     */
	boolean existsByUserIdAndSubjectId(Long userId, Long subjectId);

	//Optional<Subscription> findByUserId(Long userId);
	
	/**
     * Retrieves the list of subscriptions for a given user.
     *
     * @param userId the ID of the user
     * @return a list of Subscription entities associated with the user
     */
	List<Subscription> findByUserId(Long userId);

}
