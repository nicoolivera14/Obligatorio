package com.tuorganizacion.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTO {
  private int idUsuario;
  private String username;

  public UsuarioDTO(Long id, String username) {
    this.id = id;
    this.username = username;
  }
}
