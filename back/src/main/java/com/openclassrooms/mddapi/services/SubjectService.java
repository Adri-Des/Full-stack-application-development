package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for managing subjects.
 */
@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    /**
     * Constructs a SubjectService with the given SubjectRepository.
     *
     * @param subjectRepository the repository for accessing subject data
     */
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    /**
     * Retrieves all available subjects from the repository.
     *
     * @return a list of all subjects
     */
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
}