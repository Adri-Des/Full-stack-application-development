package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, SubscriptionRepository subscriptionRepository, SubjectRepository subjectRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    public List<Article> getFeed(Long userId) {
        List<Long> subjectIds = subscriptionRepository.findByUserId(userId)
                .stream()
                .map(subscription -> subscription.getSubject().getId())
                .collect(Collectors.toList());
        return articleRepository.findBySubjectIdInOrderByCreatedAtDesc(subjectIds);
    }
    
    
    public Article createArticle(Long userId, Long subjectId, String title, String content) {
        // Vérifiez si le sujet existe
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // Vérifiez si l'utilisateur existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Créez l'article
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setSubject(subject);
        article.setAuthor(user);
        //article.setCreatedAt(LocalDateTime.now());
        return articleRepository.save(article);
    }
    
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }
}