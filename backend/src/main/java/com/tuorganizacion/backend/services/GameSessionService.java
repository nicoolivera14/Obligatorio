package com.tuorganizacion.backend.services;

import com.tuorganizacion.backend.dto.GameSessionDTO;
import com.tuorganizacion.backend.dto.PlayerResponseDTO;
import com.tuorganizacion.backend.model.GameConstants;
import com.tuorganizacion.backend.model.GameSession;
import com.tuorganizacion.backend.model.Question;
import com.tuorganizacion.backend.model.User;
import com.tuorganizacion.backend.repository.GameSessionRepository;
import com.tuorganizacion.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class GameSessionService {
  private final GameSessionRepository gameSessionRepository;
  private final UserRepository userRepository;
  private final PlayerResponseService playerResponseService;
  private final WebSocketService webSocketService;
  private final GameSessionMapper gameSessionMapper;
  private final QuestionService questionService;

  public GameSessionService(
    GameSessionRepository gameSessionRepository,
    UserRepository userRepository,
    PlayerResponseService playerResponseService,
    WebSocketService webSocketService,
    GameSessionMapper gameSessionMapper,
    QuestionService questionService
  ) {
    this.gameSessionRepository = gameSessionRepository;
    this.userRepository = userRepository;
    this.playerResponseService = playerResponseService;
    this.webSocketService = webSocketService;
    this.gameSessionMapper = gameSessionMapper;
    this.questionService = questionService;
  }

  public GameSessionDTO startSinglePlayerSession(String username) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found"));

    GameSession gameSession = startNewSession(user, false);
    gameSessionRepository.save(gameSession);

    return gameSessionMapper.toDTO(gameSession);
  }

  public GameSessionDTO startMultiPlayerSession(String username) {
    User hostUser = userRepository.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found"));

    GameSession gameSession = startNewSession(hostUser, true);
    gameSession.setStatus("WAITING");
    gameSessionRepository.save(gameSession);

    return gameSessionMapper.toDTO(gameSession);
  }

  public void joinMultiPlayerSession(String sessionKey, String username) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found"));

    GameSession gameSession = gameSessionRepository.findBySessionKey(sessionKey)
      .orElseThrow(() -> new RuntimeException("Game session not found"));

    if (!gameSession.isMultiplayer()) {
      throw new RuntimeException("Cannot join a single-player session");
    }

    if (gameSession.getPlayers().contains(user)) {
      throw new RuntimeException("User already in game session");
    }

    if (gameSession.getPlayers().size() >= gameSession.getMaxPlayers()) {
      throw new RuntimeException("Game session is full");
    }

    gameSession.getPlayers().add(user);
    gameSessionRepository.save(gameSession);

    if (gameSession.getPlayers().size() == gameSession.getMaxPlayers()) {
      startMultiPlayerGameSession(gameSession);
    }
  }

  public Map<String, Object> handleSubmitAnswer(String sessionKey, String username, PlayerResponseDTO request) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found"));

    GameSession gameSession = gameSessionRepository.findBySessionKey(sessionKey)
      .orElseThrow(() -> new RuntimeException("Game session not found"));

    if (!gameSession.getPlayers().contains(user)) {
      throw new RuntimeException("User not in game session");
    }

    boolean correct = playerResponseService.submitPlayerResponse(
      gameSession,
      user,
      request.getQuestionId(),
      request.getSelectedAnswer(),
      request.getTimeTaken()
    );

    if (gameSession.isMultiplayer() && correct) {
      if (gameSession.getQuestions().size() == gameSession.getMaxQuestions()) {
        handleEndGameSession(sessionKey, username);
        return Map.of("finalScores", gameSession.getPlayerScores());
      }

      Map<String, Object> broadcastMessage = Map.of(
        "type", "questionAnswered",
        "correctPlayer", user.getUsername(),
        "updatedScores", gameSession.getPlayerScores()
      );

      webSocketService.broadcastUpdate(gameSession.getSessionKey(), broadcastMessage);
    }

    return Map.of(
      "correct", correct,
      "score", playerResponseService.calculateScore(correct, request.getTimeTaken()),
      "totalScore", gameSession.getPlayerScores().get(user.getUsername())
    );
  }

  public void handleEndGameSession(String sessionKey, String username) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found"));

    GameSession gameSession = gameSessionRepository.findBySessionKey(sessionKey)
      .orElseThrow(() -> new RuntimeException("Game session not found"));

    if (!gameSession.getPlayers().contains(user)) {
      throw new RuntimeException("User not in game session");
    }

    gameSession.setStatus("CLOSED");
    gameSessionRepository.save(gameSession);

    Map<String, Object> broadcastMessage = Map.of(
      "type", "sessionEnded",
      "finalScores", gameSession.getPlayerScores()
    );

    webSocketService.broadcastUpdate(gameSession.getSessionKey(), broadcastMessage);
  }

  public void handlePlayerDisconnection(String sessionKey, String username) {
    GameSession gameSession = gameSessionRepository.findBySessionKey(sessionKey)
      .orElseThrow(() -> new RuntimeException("Game session not found"));

    Map<String, Instant> disconnectedPlayers = new ConcurrentHashMap<>();
    disconnectedPlayers.put(username, Instant.now());

    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.schedule(() -> {
      if (disconnectedPlayers.containsKey(username)) {
        gameSession.setStatus("CLOSED");
        gameSessionRepository.save(gameSession);

        Map<String, Object> broadcastMessage = Map.of(
          "type", "sessionEnded",
          "reason", username + " disconnected",
          "finalScores", gameSession.getPlayerScores()
        );

        webSocketService.broadcastUpdate(gameSession.getSessionKey(), broadcastMessage);

        System.out.println("Game session " + sessionKey + "ended because " + username + " disconnected");
      }
    }, 30, TimeUnit.SECONDS);
  }

  public void spinRouletteAndFetchQuestions(String sessionKey, String username) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found"));

    GameSession gameSession = gameSessionRepository.findBySessionKey(sessionKey)
      .orElseThrow(() -> new RuntimeException("Game session not found"));

    if (!gameSession.getPlayers().contains(user)) {
      throw new RuntimeException("User not in game session");
    }

    if (!gameSession.getPlayers().get(0).equals(user)) {
      throw new RuntimeException("Only the host can spin the roulette");
    }

    if (gameSession.isSpinning()) {
      throw new RuntimeException("Roulette is already spinning");
    }

    gameSession.setSpinning(true);
    gameSessionRepository.save(gameSession);

    try {
      Random random = new Random();
      String selectedCategory = GameConstants.CATEGORIES.get(random.nextInt(GameConstants.CATEGORIES.size()));

      List<Question> questions = questionService.fetchQuestionsForCategory(1, selectedCategory);

      if (questions.isEmpty()) {
        throw new RuntimeException("No questions found for category " + selectedCategory);
      }

      Question nextQuestion = questions.get(0);
      gameSession.getQuestions().add(nextQuestion);
      gameSessionRepository.save(gameSession);

      Map<String, Object> broadcastMessage = Map.of(
        "type", "spinResult",
        "category", selectedCategory,
        "question", Map.of(
          "id", nextQuestion.getId(),
          "text", nextQuestion.getText(),
          "options", nextQuestion.getOptions()
        )
      );

      webSocketService.broadcastUpdate(gameSession.getSessionKey(), broadcastMessage);
    } finally {
      gameSession.setSpinning(false);
      gameSessionRepository.save(gameSession);
    }
  }

  private void closePreviousSessions(User user) {
    List<GameSession> activeSessions = gameSessionRepository.findActiveSessionsByUserId(user.getId());
    for (GameSession session : activeSessions) {
      session.setStatus("CLOSED");
      gameSessionRepository.save(session);
    }
  }

  private GameSession startNewSession(User user, boolean isMultiPlayer) {
    closePreviousSessions(user);

    GameSession gameSession = new GameSession();
    gameSession.setSessionKey(UUID.randomUUID().toString().substring(0, 10).toUpperCase());
    gameSession.setPlayers(new ArrayList<>(List.of(user)));
    gameSession.setMultiplayer(isMultiPlayer);

    Map<String, Integer> playerScores = new HashMap<>();
    playerScores.put(user.getUsername(), 0);
    gameSession.setPlayerScores(playerScores);

    return gameSessionRepository.save(gameSession);
  }

  private void startMultiPlayerGameSession(GameSession gameSession) {
    Map<String, Object> startMessage = Map.of(
      "type", "gameStarted",
      "players", gameSession.getPlayers().stream().map(User::getUsername).toList(),
      "firstQuestion", gameSession.getQuestions().get(0)
    );

    webSocketService.broadcastUpdate(gameSession.getSessionKey(), startMessage);
  }
}
