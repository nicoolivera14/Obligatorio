package com.tuorganizacion.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuorganizacion.backend.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Puedes agregar más consultas personalizadas si lo necesitas
    Usuario findByEmail(String email);
}
