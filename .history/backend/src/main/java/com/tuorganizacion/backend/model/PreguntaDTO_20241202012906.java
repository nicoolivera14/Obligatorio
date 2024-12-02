package com.tuorganizacion.backend.model;

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
}
 {
    
}
