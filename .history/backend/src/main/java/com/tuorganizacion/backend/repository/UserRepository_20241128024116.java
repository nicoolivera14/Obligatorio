package com.tuorganizacion.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuorganizacion.backend.model.Usuarios;

public interface UserRepository extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByUsername(String username);  // Método para buscar usuario por nombre
}
