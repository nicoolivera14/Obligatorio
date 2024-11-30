package com.tuorganizacion.backend.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "salaJuego")
public class SalaJuego {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idSalaJuego;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Usuario> jugadores;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Pregunta> preguntas;
    
    public SalaJuego(String id, List<Pregunta> preguntas) {
        this.id = id;
        this.preguntas = preguntas;
    }
}