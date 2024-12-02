package com.tuorganizacion.backend.services;

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
    this.preguntaRepository = preguntaRepository;
    this.salaJuegoRepository = salaJuegoRepository;
  }

  public boolean submitPlayerResponse(
    SalaJuego salaJuego,
    Usuario usuario,
    Long idPregunta,
    Integer respuestaSeleccionada,
    Long timeTaken
  ) {
    Pregunta pregunta = preguntaRepository.findById(idPregunta)
      .orElseThrow(() -> new RuntimeException("Question not found"));

    boolean esCorrecta = pregunta.getRespuestaCorrecta().equals(respuestaSeleccionada);

    if (SalaJuego.isMultiplayer() && pregunta.isRespondida()) {
      throw new RuntimeException("This question has already been answered.");
    }

    int score = calculateScore(esCorrecta, timeTaken);

    Respuesta respuesta = new Respuesta();
    respuesta.setSalaJuego(salaJuego);
    respuesta.setUsuario(usuario);
    respuesta.setPregunta(pregunta);
    respuesta.setRespuestaSeleccionada(respuestaSeleccionada);
    respuesta.setTimeTaken(timeTaken);
    respuesta.setScore(score);

    playerResponseRepository.save(respuesta);

    Map<String, Integer> playerScores = salaJuego.getPlayerScores();
    playerScores.merge(usuario.getUsername(), score, Integer::sum);

    if (esCorrecta) {
      pregunta.setRespondida(true);
      preguntaRepository.save(pregunta);
    }

    salaJuegoRepository.save(salaJuego);

    return @Column(nullable = false)
    private boolean multiplayer;;
  }

  public int calculateScore(boolean isCorrect, long timeTaken) {
    if (!isCorrect) {
      return 0;
    }

    double penalty = timeTaken * TIME_PENALTY_FACTOR;
    return Math.max((int) (BASE_SCORE - penalty), 1);
  }
}