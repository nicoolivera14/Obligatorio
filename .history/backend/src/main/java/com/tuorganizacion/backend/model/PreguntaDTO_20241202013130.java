package com.tuorganizacion.backend.model;

import com.tuorganizacion.backend.model.PreguntaOpcion;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreguntaDTO {
    private String textoPregunta;
    private List<String> opciones; // Solo capturamos los textos aquí
    private String categoria;
    private int respuestaCorrecta;

    public Pregunta convertirPregunta(PreguntaDTO dto) {
        Pregunta pregunta = new Pregunta();
        pregunta.setTextoPregunta(dto.getTextoPregunta());
        pregunta.setCategoria(dto.getCategoria());
        pregunta.setRespuestaCorrecta(dto.getRespuestaCorrecta());
    
        // Convertimos las opciones
        List<PreguntaOpcion> opciones = dto.getOpciones().stream()
            .map(texto -> new PreguntaOpcion(texto)) // Constructor de PreguntaOpcion
            .toList();
    
        pregunta.setOpciones(opciones);
    
        // Establecemos la relación inversa para PreguntaOpcion
        for (PreguntaOpcion opcion : opciones) {
            opcion.setPregunta(pregunta);
        }
    
        return pregunta;
    }
    
}
