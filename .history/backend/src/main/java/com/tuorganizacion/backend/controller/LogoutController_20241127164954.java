package com.tuorganizacion.backend.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class LogoutController {

    @GetMapping("/api/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // Invalida la sesión actual
        if (session != null) {
            session.invalidate();
        }
        // Devuelve una respuesta JSON
        return ResponseEntity.ok("Logged out successfully");
    }
}
