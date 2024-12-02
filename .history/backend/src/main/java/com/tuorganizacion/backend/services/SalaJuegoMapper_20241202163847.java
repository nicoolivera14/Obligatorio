package com.tuorganizacion.backend.services;

package com.trivia.service;

import com.trivia.dto.GameSessionDTO;
import com.trivia.dto.PlayerDTO;
import com.trivia.model.GameSession;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SalaJuegoMapper {
  public SalaJuegoDTO toDTO(SalaJuego salaJuego) {
    SalaJuegoDTO dto = new SalaJuego();

    dto.setSessionKey(gameSession.getSessionKey());
    dto.setPlayers(gameSession.getPlayers().stream()
      .map(user -> new PlayerDTO(user.getId(), user.getUsername()))
      .collect(Collectors.toList())
    );
    dto.setStatus(gameSession.getStatus());
    dto.setCreatedAt(gameSession.getCreatedAt());

    return dto;
  }
}