package com.tuorganizacion.backend.services;

import com.tuorganizacion.backend.dto.PreguntaDTO;
import com.tuorganizacion.backend.model.Pregunta;
import org.springframework.stereotype.Service;

@Service
public class QuestionMapper {
  public PreguntaDTO toDTO(Pregunta pregunta) {
    QuestionDTO dto = new QuestionDTO();

    dto.setId(pregunta.getId());
    dto.setText(pregunta.getText());
    dto.setOptions(pregunta.getOptions());

    return dto;
  }
}