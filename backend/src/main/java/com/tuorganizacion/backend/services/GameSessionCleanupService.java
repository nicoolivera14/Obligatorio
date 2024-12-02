package com.tuorganizacion.backend.services;

import com.tuorganizacion.backend.model.GameSession;
import com.tuorganizacion.backend.model.Question;
import com.tuorganizacion.backend.repository.GameSessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class GameSessionCleanupService {
  private final GameSessionRepository gameSessionRepository;
  private final GameSessionService gameSessionService;

  public GameSessionCleanupService(GameSessionRepository gameSessionRepository, GameSessionService gameSessionService) {
    this.gameSessionRepository = gameSessionRepository;
    this.gameSessionService = gameSessionService;
  }

  @Scheduled(fixedRate = 3600000)
  public void cleanupInactiveSessions() {
    Instant cutoffTime = Instant.now().minusSeconds(300);
    List<GameSession> inactiveSessions = gameSessionRepository.findByStatusAndUpdatedAtBefore("WAITING", cutoffTime);

    for (GameSession session : inactiveSessions) {
      session.setStatus("CLOSED");
      gameSessionRepository.save(session);
      System.out.println("Session " + session.getSessionKey() + " ended due to inactivity.");
    }
  }

  @Scheduled(fixedRate = 3600000)
  public void cleanupFinishedSessions() {
    List<GameSession> activeSessions = gameSessionRepository.findByStatus("IN_PROGRESS");

    for (GameSession session : activeSessions) {
      boolean allQuestionsAnswered = session.getQuestions().stream().allMatch(Question::isAnswered);

      if (allQuestionsAnswered) {
        gameSessionService.handleEndGameSession(session.getSessionKey(), session.getPlayers().get(0).getUsername());
      }
    }
  }
}
