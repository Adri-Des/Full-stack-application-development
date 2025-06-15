package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for managing Subject entities in the database.
 * <p>
 * Extends JpaRepository to provide standard CRUD operations for the Subject entity.
 * </p>
 */
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
