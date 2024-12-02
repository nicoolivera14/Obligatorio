package com.tuorganizacion.backend.repository;

import com.tuorganizacion.backend.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;;

public interface ResRepository extends JpaRepository<Respuesta, Long> {
}

