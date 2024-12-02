package com.tuorganizacion.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuorganizacion.backend.model.Pregunta;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
}
