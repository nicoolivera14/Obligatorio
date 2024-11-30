package com.tuorganizacion.backend.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Usuarios")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, name = "name")
    @NotBlank(message = "Nombre es requerido.")
    @Size(min = 3, max = 50, message = "Nombre debe contener entre 3 y 50 carácteres.")
    private String name;

    @Column(nullable = false, name = "password")
    @NotBlank(message = "Password es requerido.")
    @Size(min = 8, message = "Password debe tener al menos 8 carácteres.")
    private String password;

    @Column(name = "highscore")
    private int highscore;

    // Default constructor
    public User() {}

    // Constructor with parameters
    public User(String name, String password, int highscore) {
        this.name = name;
        this.password = password;
        this.highscore = highscore;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    // Optional: Override toString for debugging
    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", highscore=" + highscore +
               '}';
    }
}

