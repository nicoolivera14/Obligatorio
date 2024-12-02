package com.tuorganizacion.backend.model;

import java.util.List;
import java.util.ArrayList;

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

    @Column(name = "textoPregunta", nullable = false)
    private String textoPregunta;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL)
    private List<PreguntaOpcion> opciones = new ArrayList<>();

    @Column( nullable = false)
    private boolean respondida = false;
    
    @JsonIgnore
    @Column( nullable = false)
    private int  respuestaCorrecta;

    @ManyToOne
    @JoinColumn(name = "idSalaJuego")
    private SalaJuego salaJuego;

    @OneToOne
    @JoinColumn(name = "idRespuesta")
    private Respuesta respuesta;

    @Column(nullable = false)
    private String categoria;
}
