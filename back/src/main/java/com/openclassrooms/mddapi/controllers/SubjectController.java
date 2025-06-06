package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Subject;

import com.openclassrooms.mddapi.services.SubjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller responsible for handling requests related to subjects.
 */
@RestController
@RequestMapping("/api/subjects")
@Tag(name = "Subjects", description = "Endpoints for retrieving available subjects")
public class SubjectController {
	
    private final SubjectService subjectService;

    /**
     * Constructs a new SubjectController with the provided SubjectService.
     *
     * @param subjectService the subject service to use for data retrieval
     */
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * Retrieves a list of all available subjects.
     *
     * @return a list of subjects
     */
    @Operation(summary = "Get all subjects", description = "Returns a list of all subjects available in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subjects retrieved successfully")
    })
    @GetMapping
    public List<Subject> getSubjects() {
        return subjectService.getAllSubjects();
    }
}