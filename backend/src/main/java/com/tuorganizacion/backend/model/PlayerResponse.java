package com.tuorganizacion.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "player_responses")
public class PlayerResponse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "game_session_id", nullable = false)
  private GameSession gameSession;

  @ManyToOne
  @JoinColumn(name = "player_id", nullable = false)
  private User player;

  @ManyToOne
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;

  @Column(nullable = false)
  private Integer selectedAnswer;

  @Column
  private Long timeTaken;

  @Column Integer score;
}