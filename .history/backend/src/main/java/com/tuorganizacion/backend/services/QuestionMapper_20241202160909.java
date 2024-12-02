package com.tuorganizacion.backend.services;

import com.trivia.dto.QuestionDTO;
import com.trivia.model.Question;
import org.springframework.stereotype.Service;

@Service
public class QuestionMapper {
  public QuestionDTO toDTO(Question question) {
    QuestionDTO dto = new QuestionDTO();

    dto.setId(question.getId());
    dto.setText(question.getText());
    dto.setOptions(question.getOptions());

    return dto;
  }
}