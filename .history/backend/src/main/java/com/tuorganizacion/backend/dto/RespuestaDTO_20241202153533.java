package com.tuorganizacion.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RespuestaDTO {
  Integer idPregunta;
  Integer selectedAnswer;
  Long timeTaken;
}