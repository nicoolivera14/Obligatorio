package com.tuorganizacion.backend.services;

import com.tuorganizacion.backend.dto.SalaJuegoDTO;
import com.tuorganizacion.backend.dto.UsuarioDTO;
import com.tuorganizacion.backend.model.SalaJuego;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SalaJuegoMapper {
  public SalaJuegoDTO toDTO(SalaJuego salaJuego) {
    SalaJuegoDTO dto = new SalaJuegoDTO();

    dto.setSalaKey(salaJuego.getSalaKey());
    dto.setJugadores(salaJuego.getJugadores().stream()
      .map(usuario -> new UsuarioDTO(usuario.getIdUsuario(), usuario.getUsername()))
      .collect(Collectors.toList())
    );

    return dto;
  }
}