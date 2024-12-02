package com.tuorganizacion.backend.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "opcion")
public class PreguntaOpcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOpcion;

    @ManyToOne
    @JoinColumn(name = "idPregunta", nullable = false)
    private Pregunta pregunta;

    @Column( nullable = false, unique = true)
    private String textoOpcion;

    @OneToMany(mappedBy = "preguntaOpcion")
    private List<Respuesta> respuestas;
}
