package com.tuorganizacion.backend.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "game_sessions")
public class GameSession {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String sessionKey;

  @ManyToMany
  private List<User> players;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Question> questions;

  @Column(nullable = false)
  private boolean multiplayer;

  @OneToMany(mappedBy = "gameSession", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PlayerResponse> playerResponses;

  @ElementCollection
  @CollectionTable(name = "game_session_scores", joinColumns = @JoinColumn(name = "game_session_id"))
  @MapKeyColumn(name = "player_username")
  @Column(name = "score", nullable = false)
  private Map<String, Integer> playerScores = new HashMap<>();

  @Column(nullable = false)
  private String status = "IN_PROGRESS";

  @Column(nullable = false)
  private Instant createdAt = Instant.now();

  @Column(nullable = false)
  private Instant updatedAt = Instant.now();

  @Column(nullable = false)
  private int maxPlayers = 2;

  @Column(nullable = false)
  private int maxQuestions = 10;

  @Column(nullable = false)
  private boolean spinning = false;

  @PrePersist
  @PreUpdate
  private void updateTimestamps() {
    this.updatedAt = Instant.now();
  }
}
