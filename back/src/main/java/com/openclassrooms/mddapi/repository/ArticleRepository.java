package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findBySubjectIdInOrderByCreatedAtDesc(List<Long> subjectIds);
}