package com.tuorganizacion.backend.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
