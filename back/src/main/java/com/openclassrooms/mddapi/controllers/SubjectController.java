package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.services.SubjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
	
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getSubjects() {
        return subjectService.getAllSubjects();
    }
}