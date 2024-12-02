package com.tuorganizacion.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTO {
  private int idUsuario;
  private String username;

  public UsuarioDTO(Integer id, String username) {
    this.idUsuario = id;
    this.username = username;
  }
}
