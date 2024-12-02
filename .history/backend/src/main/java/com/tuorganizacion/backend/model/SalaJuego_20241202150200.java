package com.tuorganizacion.backend.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

    @Column(nullable = false)
  private String Sala;

    @OneToMany(mappedBy = "salaJuego", cascade = CascadeType.ALL)
    private List<Usuario> jugadores;

    @OneToMany(mappedBy = "salaJuego", cascade = CascadeType.ALL)
    private List<Pregunta> preguntas;
    

}
