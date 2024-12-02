package com.trivia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "questions")
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 500)
  private String text;

  @ElementCollection
  @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
  @Column(name = "option_text", nullable = false)
  private List<String> options;

  @Column(nullable = false)
  private Integer correctAnswer;

  @Column(nullable = false)
  private boolean answered = false;

  @Column(nullable = false)
  private String category;
}
