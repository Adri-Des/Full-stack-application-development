package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for managing user subscriptions to subjects.
 */
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Constructs a SubscriptionService with the given SubscriptionRepository.
     *
     * @param subscriptionRepository the repository used to manage subscriptions
     */
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Retrieves all subscriptions for a given user.
     *
     * @param userId the ID of the user
     * @return a list of subscriptions associated with the user
     */
    public List<Subscription> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    /**
     * Adds a subscription for the specified user to a specific subject.
     *
     * @param userId    the ID of the user subscribing
     * @param subjectId the ID of the subject to subscribe to
     * @return the created Subscription object
     * @throws RuntimeException if the user is already subscribed to the subject
     */
    public Subscription addSubscription(Long userId, Long subjectId) {
        if (subscriptionRepository.existsByUserIdAndSubjectId(userId, subjectId)) {
            throw new RuntimeException("Already subscribed");
        }
        Subscription subscription = new Subscription();
        subscription.setUser(new User(userId)); 
        subscription.setSubject(new Subject(subjectId)); 
        return subscriptionRepository.save(subscription);
    }

    /**
     * Removes a subscription of the user from a specific subject, if it exists.
     *
     * @param userId    the ID of the user
     * @param subjectId the ID of the subject to unsubscribe from
     */
    public void removeSubscription(Long userId, Long subjectId) {
        subscriptionRepository.findByUserIdAndSubjectId(userId, subjectId)
                .ifPresent(subscriptionRepository::delete);
    }
}