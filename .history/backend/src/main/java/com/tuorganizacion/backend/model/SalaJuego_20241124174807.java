package com.tuorganizacion.backend.model;

import java.util.List;

public class SalaJuego {
    private String id;
    private List<String> jugadores;
    private List<Pregunta> preguntas;
    
    public SalaJuego(String id, List<Pregunta> preguntas) {
        this.id = id;
        this.preguntas = preguntas;
    }
    
    // Getters y setters
    public String getId() {
        return id;
    }
    public List<String> getJugadores() {
        return jugadores;
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
    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
    

}
