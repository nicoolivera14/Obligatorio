package com.trivia.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerResponseDTO {
  Long questionId;
  Integer selectedAnswer;
  Long timeTaken;
}
