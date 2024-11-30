package com.tuorganizacion.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Nombre es requerido.")
    @Size(min = 3, max = 50, message = "Nombre debe contener entre 3 y 50 carácteres.")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password es requerido.")
    @Size(min = 8, message = "Password debe tener al menos 8 carácteres.")
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_sala_juego")
    private SalaJuego salaJuego;

    @OneToMany(mappedBy = "id_opcion", cascade = CascadeType.ALL)
    private PreguntaOpcion preguntaOpcion;
}

