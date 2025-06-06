package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for managing articles and providing feed content for users.
 */
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    /**
     * Constructs an ArticleService with the required repositories.
     *
     * @param articleRepository       repository for managing articles
     * @param subscriptionRepository  repository for managing user subscriptions
     * @param subjectRepository       repository for retrieving subjects
     * @param userRepository          repository for retrieving users
     */
    public ArticleService(ArticleRepository articleRepository, SubscriptionRepository subscriptionRepository, SubjectRepository subjectRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a personalized article feed for the given user based on their subscriptions.
     *
     * @param userId the ID of the user whose feed should be retrieved
     * @return a list of articles related to the user's subscribed subjects, ordered by creation date (most recent first)
     */
    public List<Article> getFeed(Long userId) {
        List<Long> subjectIds = subscriptionRepository.findByUserId(userId)
                .stream()
                .map(subscription -> subscription.getSubject().getId())
                .collect(Collectors.toList());
        return articleRepository.findBySubjectIdInOrderByCreatedAtDesc(subjectIds);
    }
    
    /**
     * Creates and saves a new article.
     *
     * @param userId    the ID of the user creating the article
     * @param subjectId the ID of the subject the article belongs to
     * @param title     the title of the article
     * @param content   the content of the article
     * @return the saved Article instance
     * @throws RuntimeException if the subject or user does not exist
     */
    public Article createArticle(Long userId, Long subjectId, String title, String content) {
        // Check if subject exist
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // Check if user exist
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create article
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setSubject(subject);
        article.setAuthor(user);
        return articleRepository.save(article);
    }
    
    /**
     * Retrieves an article by its ID.
     *
     * @param id the ID of the article
     * @return the Article instance if found
     * @throws RuntimeException if the article does not exist
     */
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }
}