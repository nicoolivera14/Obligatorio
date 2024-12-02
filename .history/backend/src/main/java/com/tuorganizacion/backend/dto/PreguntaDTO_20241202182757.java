package com.trivia.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuestionDTO {
  private Long id;
  private String text;
  private List<String> options;
}
