package com.tuorganizacion.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuorganizacion.backend.model.PlayerResponse;

public interface PlayerResponseRepository extends JpaRepository<PlayerResponse, Long> {
}
