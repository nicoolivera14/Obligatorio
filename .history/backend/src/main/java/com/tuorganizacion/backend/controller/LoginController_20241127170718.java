package com.tuorganizacion.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final PersistenceController persistenceController;

    // Inyección de dependencias a través del constructor
    public LoginController(PersistenceController persistenceController) {
        this.persistenceController = persistenceController;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String name, @RequestParam String password, HttpSession session) {
        // Buscar usuario por nombre y contraseña
        User user = persistenceController.getUsersList().stream()
            .filter(u -> u.getName().equals(name) && u.getPassword().equals(password))
            .findFirst()
            .orElse(null);

        if (user != null) {
            session.setAttribute("user", user);
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }
}