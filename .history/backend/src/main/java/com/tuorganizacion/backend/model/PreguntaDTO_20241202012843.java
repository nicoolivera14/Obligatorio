package com.tuorganizacion.backend.model;

import lombok.Getter;
import lombok.Setter;

public @Getter
@Setter
public class PreguntaDTO {
    private String textoPregunta;
    private List<String> opciones; // Solo capturamos los textos aquí
    private String categoria;
    private int respuestaCorrecta;
}
 {
    
}
