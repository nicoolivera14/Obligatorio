package com.tuorganizacion.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "opcion")
public class PreguntaOpciones {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOpcion;

    @ManyToOne
    @JoinColumn(name = "idPregunta", nullable = false)
    private int idPregunta;

    @Column(nullable = false)
    private String texto;
}
