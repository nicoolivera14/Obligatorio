package com.tuorganizacion.backend.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tuorganizacion.backend.dto.GameSessionDTO;
import com.tuorganizacion.backend.dto.PlayerDTO;
import com.tuorganizacion.backend.model.GameSession;

@Service
public class GameSessionMapper {
  public GameSessionDTO toDTO(GameSession gameSession) {
    GameSessionDTO dto = new GameSessionDTO();

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
