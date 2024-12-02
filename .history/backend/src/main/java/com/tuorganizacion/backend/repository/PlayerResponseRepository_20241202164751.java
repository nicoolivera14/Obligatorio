package com.tuorganizacion.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuorganizacion.backend.model.Respuesta;;

public interface PlayerResponseRepository extends JpaRepository<Respuesta, Long> {
}

