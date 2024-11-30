package com.tuorganizacion.backend.controller;

import com.tuorganizacion.backend.model.Usuarios;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PersistenceController persistenceController;

    // Inyección de dependencias a través del constructor
    public AuthController(PersistenceController persistenceController) {
        this.persistenceController = persistenceController;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // Buscar usuario por nombre y contraseña
        Usuarios user = persistenceController.getUsersList().stream()
            .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
            .findFirst()
            .orElse(null);

        if (user != null) {
            session.setAttribute("user", user);
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }

    @Getter
    @Setter
    public static class SolicitudLogin {
        private String username;
        private String password;  
    }
}