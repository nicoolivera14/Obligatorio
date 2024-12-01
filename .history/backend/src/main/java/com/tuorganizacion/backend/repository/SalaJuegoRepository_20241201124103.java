package com.tuorganizacion.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuorganizacion.backend.model.Usuario;

public interface SalaJuegoRepository extends JpaRepository<Usuario, Long> {
    Optional<SalaJuego> findById(int idSala);
}
