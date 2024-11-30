package com.tuorganizacion.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuorganizacion.backend.model.Usuarios;

@Repository
public interface UserRepository extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByUsername(String username);  // Método para buscar usuario por nombre
}
