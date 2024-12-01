package com.tuorganizacion.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuorganizacion.backend.model.SalaJuego;

public interface SalaJuegoRepository extends JpaRepository<Salaj, Long> {
    Optional<SalaJuego> findById(int idSala);
}
