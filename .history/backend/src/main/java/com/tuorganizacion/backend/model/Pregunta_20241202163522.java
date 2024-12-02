package com.tuorganizacion.backend.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "preguntas")
@Entity
@Table(name = "preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPregunta;

    @Column(nullable = false)
    private String textoPregunta;

    @ElementCollection
    @CollectionTable(name = "pregunta_opcion", joinColumns = @JoinColumn(name = "idPregunta"))
    @Column(name = "textoOpcion", nullable = false)
    private List<String> opciones;

    @Column(nullable = false)
    private boolean respondida = false;

    @Column(nullable = false)
    private int respuestaCorrecta;

    @Column(nullable = false)
    private String categoria;
}

