package com.tuorganizacion.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "opcion")
public class PreguntaOpciones {
    private int id;
    private int idPregunta;
    private String texto;
}
