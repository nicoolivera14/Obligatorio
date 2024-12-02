package com.trivia.model;

import jakarta.persistence.*;
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