package com.tuorganizacion.backend.repository;

import com.tuorganizacion.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Métodos personalizados si los necesitas
}
