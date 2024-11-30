package com.tuorganizacion.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuorganizacion.backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);  // Método para buscar usuario por nombre
}
