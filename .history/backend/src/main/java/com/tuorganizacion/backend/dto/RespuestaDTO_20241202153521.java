package com.tuorganizacion.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RespuestaDTO {
  Long idPregunta;
  Integer selectedAnswer;
  Long timeTaken;
}