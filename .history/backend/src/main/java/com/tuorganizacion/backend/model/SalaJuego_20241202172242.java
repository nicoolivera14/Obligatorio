package com.tuorganizacion.backend.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyColumn;
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
    private String SalaKey;

    @ManyToMany
    @JoinTable(name = "salaJuego_jugadores", joinColumns = @JoinColumn(name = "idSalaJuego"), inverseJoinColumns = @JoinColumn(name = "idUsuario"))
    private List<Usuario> jugadores;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Pregunta> preguntas;
    
    @OneToMany(mappedBy = "salaJuego", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;

    @Column(nullable = false)
    private boolean multiplayer;

    @ElementCollection
    @CollectionTable(name = "salaJuego_scores", joinColumns = @JoinColumn(name = "idSalaJuego"))
    @MapKeyColumn(name = "usuarioUsername")
    @Column(name = "score", nullable = false)
    private Map<String, Integer> playerScores = new HashMap<>();
}
