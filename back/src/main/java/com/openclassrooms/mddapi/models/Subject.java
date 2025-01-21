package com.openclassrooms.mddapi.models;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    
}