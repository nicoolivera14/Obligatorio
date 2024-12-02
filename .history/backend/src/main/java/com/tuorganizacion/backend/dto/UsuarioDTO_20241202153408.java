package com.tuorganizacion.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTO {
  private int idUsuario;
  private String username;

  public UsuarioDTO(Integer idUsuario, String username) {
    this.idUsuario = idUsuario;
    this.username = username;
  }
}
