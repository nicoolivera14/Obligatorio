package com.tuorganizacion.backend.model;

@Getter
@Setter
public class PreguntaDTO {
    private String textoPregunta;
    private String categoria;
    private List<String> opciones; // Cambiado para reflejar el JSON
    private int respuestaCorrecta; // Índice de la respuesta correcta
}
