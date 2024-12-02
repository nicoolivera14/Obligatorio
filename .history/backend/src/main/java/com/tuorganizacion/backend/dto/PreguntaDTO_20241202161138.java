package com.tuorganizacion.backend.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PreguntaDTO {
  private Integer id;
  private String texto;
  private List<String> opciones;
}
