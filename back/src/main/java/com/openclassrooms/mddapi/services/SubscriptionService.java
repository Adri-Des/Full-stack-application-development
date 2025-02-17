package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public Subscription addSubscription(Long userId, Long subjectId) {
        if (subscriptionRepository.existsByUserIdAndSubjectId(userId, subjectId)) {
            throw new RuntimeException("Already subscribed");
        }
        Subscription subscription = new Subscription();
        subscription.setUser(new User(userId)); 
        subscription.setSubject(new Subject(subjectId)); 
        return subscriptionRepository.save(subscription);
    }

    public void removeSubscription(Long userId, Long subjectId) {
        subscriptionRepository.findByUserIdAndSubjectId(userId, subjectId)
                .ifPresent(subscriptionRepository::delete);
    }
}