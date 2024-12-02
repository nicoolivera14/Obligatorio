package com.tuorganizacion.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuorganizacion.backend.model.SalaJuego;

public interface SalaJuegoRepository extends JpaRepository<SalaJuego, String> {
    Optional<SalaJuego> findByIdSalaJuego(String idSalaJuego);
    Optional<SalaJuego> findBySessionKey(String salaKey);
}
