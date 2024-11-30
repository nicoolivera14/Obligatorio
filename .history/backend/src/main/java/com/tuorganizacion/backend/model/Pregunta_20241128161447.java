package com.tuorganizacion.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private int id;

    @Column(name = "textoPregunta", nullable = false)
    private String textoPregunta;

    @OneToMany(mappedBy = "opciones", cascade = CascadeType.ALL)
    private List<String> opciones;

    @Column(name = "respondida", nullable = false)
    private boolean respondida = false;
    
    @JsonIgnore
    @Column(name = "respuestaCorrecta", nullable = false)
    private int  respuestaCorrecta;
}
