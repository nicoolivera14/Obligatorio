package com.tuorganizacion.backend.model;

import java.util.HashMap;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "sala_juego")
public class SalaJuego {
    @Id
    private String idSalaJuego;

    @OneToMany(mappedBy = "salaJuego", cascade = CascadeType.ALL)
    private HashMap jugadores = new HashMap();

    @OneToMany(mappedBy = "salaJuego", cascade = CascadeType.ALL)
    private List<Pregunta> preguntas;
    
    public SalaJuego(String idSalaJuego, List<Pregunta> preguntas, List<Usuario> jugadores) {
        this.idSalaJuego = idSalaJuego;
        this.preguntas = preguntas;
        this.jugadores = jugadores;
    }
}
