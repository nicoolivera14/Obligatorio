package com.tuorganizacion.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private int id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Nombre es requerido.")
    @Size(min = 3, max = 50, message = "Nombre debe contener entre 3 y 50 carácteres.")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password es requerido.")
    @Size(min = 8, message = "Password debe tener al menos 8 carácteres.")
    private String password;
}
