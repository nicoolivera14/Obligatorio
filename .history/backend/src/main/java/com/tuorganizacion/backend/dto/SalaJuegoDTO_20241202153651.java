package com.tuorganizacion.backend.dto;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SalaJuegoDTO {
  private String sessionKey;
  private List<UsuarioDTODTO> players;
  private String status;
  private Instant createdAt;
}