package com.tuorganizacion.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "pregunta")
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPregunta;

    @Column(nullable = false)
    private String textoPregunta;

    @E
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL)
    private List<PreguntaOpcion> opciones;

    @Column(nullable = false)
    private boolean respondida = false;
    
    @JsonIgnore
    @Column( nullable = false)
    private int  respuestaCorrecta;

    @ManyToOne
    @JoinColumn(name = "idSalaJuego")
    private SalaJuego salaJuego;

    @Column(nullable = false)
    private String categoria;
}
