package com.tuorganizacion.backend.model;

import java.util.List;

public class SalaJuego {
    private String id;
    private List<String> jugadores;
    private List<Pregunta> preguntas;
    private int preguntaActual;
    
    public SalaJuego(String id, List<Pregunta> preguntas) {
        this.id = id;
        this.preguntas = preguntas;
        this.preguntaActual = 0;
    }
    
    // Getters y setters
    public String getId() {
        return id;
    }
    public List<String> getJugadores() {
        return jugadores;
    }
    public int getPreguntaActual() {
        return preguntaActual;
    }
    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setJugadores(List<String> jugadores) {
        this.jugadores = jugadores;
    }
    public void setPreguntaActual(int preguntaActual) {
        this.preguntaActual = preguntaActual;
    }
    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}
