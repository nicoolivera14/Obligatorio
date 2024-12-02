package com.tuorganizacion.backend.services;

package com.trivia.service;

import com.tuorganizacion.backend.model.SalaJuego;
import com.tuorganizacion.backend.model.Respuesta;
import com.tuorganizacion.backend.model.Pregunta;
import com.tuorganizacion.backend.model.Usuario;
import com.tuorganizacion.backend.repository.SalaJuegoRepository;
import com.tuorganizacion.backend.repository.PlayerResponseRepository;
import com.tuorganizacion.backend.repository.PreguntaRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayerResponseService {
  private static final int BASE_SCORE = 10;
  private static final double TIME_PENALTY_FACTOR = 0.001;

  private final PlayerResponseRepository playerResponseRepository;
  private final PreguntaRepository preguntaRepository;
  private final SalaJuegoRepository salaJuegoRepository;

  public PlayerResponseService(
    PlayerResponseRepository playerResponseRepository,
    PreguntaRepository preguntaRepository,
    SalaJuegoRepository salaJuegoRepository
  ) {
    this.playerResponseRepository = playerResponseRepository;
    this.preguntaRepository = questionRepository;
    this.gameSessionRepository = gameSessionRepository;
  }

  public boolean submitPlayerResponse(
    GameSession gameSession,
    User player,
    Long questionId,
    Integer selectedAnswer,
    Long timeTaken
  ) {
    Question question = questionRepository.findById(questionId)
      .orElseThrow(() -> new RuntimeException("Question not found"));

    boolean isCorrect = question.getCorrectAnswer().equals(selectedAnswer);

    if (gameSession.isMultiplayer() && question.isAnswered()) {
      throw new RuntimeException("This question has already been answered.");
    }

    int score = calculateScore(isCorrect, timeTaken);

    PlayerResponse response = new PlayerResponse();
    response.setGameSession(gameSession);
    response.setPlayer(player);
    response.setQuestion(question);
    response.setSelectedAnswer(selectedAnswer);
    response.setTimeTaken(timeTaken);
    response.setScore(score);

    playerResponseRepository.save(response);

    Map<String, Integer> playerScores = gameSession.getPlayerScores();
    playerScores.merge(player.getUsername(), score, Integer::sum);

    if (isCorrect) {
      question.setAnswered(true);
      questionRepository.save(question);
    }

    gameSessionRepository.save(gameSession);

    return isCorrect;
  }

  public int calculateScore(boolean isCorrect, long timeTaken) {
    if (!isCorrect) {
      return 0;
    }

    double penalty = timeTaken * TIME_PENALTY_FACTOR;
    return Math.max((int) (BASE_SCORE - penalty), 1);
  }
}