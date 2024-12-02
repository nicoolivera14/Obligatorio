package com.tuorganizacion.backend.services;

package com.trivia.service;

import com.tu.dto.GameSessionDTO;
import com.trivia.dto.PlayerDTO;
import com.trivia.model.GameSession;
import com.tuorganizacion.backend.dto.SalaJuegoDTO;
import com.tuorganizacion.backend.model.SalaJuego;
import com.tuorganizacion.backend.model.Usuario;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SalaJuegoMapper {
  public SalaJuegoDTO toDTO(SalaJuego salaJuego) {
    SalaJuegoDTO dto = new SalaJuego();

    dto.setSalaKey(salaJuego.getSalaKey());
    dto.setJugadores(salaJuego.getJugadores().stream()
      .map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getUsername()))
      .collect(Collectors.toList())
    );

    return dto;
  }
}