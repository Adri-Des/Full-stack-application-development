package com.openclassrooms.mddapi.models;


import lombok.*;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
   /* @OneToMany(mappedBy = "subject")
    private List<Article> articles;*/

    public Subject(Long id) {
        this.id = id;
    }
    
}