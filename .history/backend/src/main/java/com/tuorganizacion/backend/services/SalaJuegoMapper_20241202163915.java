package com.tuorganizacion.backend.services;

package com.trivia.service;

import com.trivia.dto.GameSessionDTO;
import com.trivia.dto.PlayerDTO;
import com.trivia.model.GameSession;
import com.tuorganizacion.backend.model.SalaJuego;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SalaJuegoMapper {
  public SalaJuegoDTO toDTO(SalaJuego salaJuego) {
    SalaJuegoDTO dto = new SalaJuego();

    dto.set(salaJuego.getSalaKey());
    dto.setPlayers(salaJuego.getPlayers().stream()
      .map(user -> new PlayerDTO(user.getId(), user.getUsername()))
      .collect(Collectors.toList())
    );

    return dto;
  }
}