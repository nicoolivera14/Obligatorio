package com.tuorganizacion.backend.dto;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SalaJuegoDTO {
  private String salaKey;
  private List<UsuarioDTO> jugadores;
  private String status;
}