package com.tuorganizacion.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PreguntaDTO {
  private Long id;
  private String text;
  private List<String> options;
}
