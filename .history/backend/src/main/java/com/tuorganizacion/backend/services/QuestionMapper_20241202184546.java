package com.tuorganizacion.backend.services;

import org.springframework.stereotype.Service;

import com.tuorganizacion.backend.dto.QuestionDTO;
import com.tuorganizacion.backend.model.Question;

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