package com.tuorganizacion.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RespuestaDTO {
  Long questionId;
  Integer selectedAnswer;
  Long timeTaken;
}