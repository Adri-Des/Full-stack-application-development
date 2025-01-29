package com.openclassrooms.mddapi.models;

import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "subscriptions")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Subscription {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  
	  @ManyToOne
	  @JoinColumn(name = "user_id", nullable = false)
	  private User user;
	  
	  @ManyToOne
	  @JoinColumn(name = "subject_id", nullable = false)
	  private Subject subject;
	  
	  
	  @CreationTimestamp
	  @Column(name = "created_at", updatable = false)
	  private LocalDateTime createdAt;

	  @UpdateTimestamp
	  @Column(name = "updated_at")
	  private LocalDateTime updatedAt;
	  
	  

}
