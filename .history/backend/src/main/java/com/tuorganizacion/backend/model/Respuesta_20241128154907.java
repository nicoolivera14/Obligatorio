package com.tuorganizacion.backend.model;

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
@Table(name = "respuesta")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idPregunta", nullable = false)
    private int idPregunta;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private int idUsuario;

    @ManyToOne
    @JoinColumn(name = "idOpcion", nullable = false)
    private int idOpcion;
    
    
}