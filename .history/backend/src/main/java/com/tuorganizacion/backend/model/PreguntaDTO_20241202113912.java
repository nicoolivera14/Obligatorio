package com.tuorganizacion.backend.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreguntaDTO {
    private String textoPregunta;
    private String categoria;
    private List<String> opciones; // Cambiado para reflejar el JSON
    private int respuestaCorrecta; // Índice de la respuesta correcta

    public Pregunta convertirDTOaEntidad(PreguntaDTO dto) {
    Pregunta pregunta = new Pregunta();
    pregunta.setTextoPregunta(dto.getTextoPregunta());
    pregunta.setCategoria(dto.getCategoria());
    pregunta.setRespondida(false);

    List<PreguntaOpcion> opciones = new ArrayList<>();
    for (int i = 0; i < dto.getOpciones().size(); i++) {
        PreguntaOpcion opcion = new PreguntaOpcion();
        opcion.setTextoOpcion(dto.getOpciones().get(i));
        opcion.setPregunta(pregunta); // Relación bidireccional
        opciones.add(opcion);

        // Si el índice coincide con la respuesta correcta
        if (i == dto.getRespuestaCorrecta()) {
            pregunta.setRespuestaCorrecta(i); // O almacenar directamente el texto
        }
    }

    pregunta.setOpciones(opciones);
    return pregunta;
}

}
