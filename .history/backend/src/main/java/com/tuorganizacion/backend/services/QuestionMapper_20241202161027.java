package com.tuorganizacion.backend.services;

import com.tuorganizacion.backend.dto.PreguntaDTO;
import com.tuorganizacion.backend.model.Pregunta;
import org.springframework.stereotype.Service;

@Service
public class QuestionMapper {
  public PreguntaDTO toDTO(Pregunta pregunta) {
    PreguntaDTO dto = new PreguntaDTO();

    dto.setId(pregunta.getId());
    dto.setText(pregunta.getText());
    dto.setOptions(pregunta.getOptions());

    return dto;
  }
}