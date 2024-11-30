package com.tuorganizacion.backend.controller;

import com.tuorganizacion.backend.model.Usuarios;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SignupController {

    @Autowired
    private PersistenceController persistenceController;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();
        String confirmPassword = signupRequest.getConfirmPassword();

        List<Usuarios> users = persistenceController.getUsersList();
        if (users == null) {
            return ResponseEntity.badRequest().body("Database error: Unable to retrieve user list.");
        }

        // Password validation
        if (password.length() < 8 || !password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters and match the confirmation.");
        }

        // Create new user
        Usuarios user = new Usuarios();
        user.setUsername(username);
        user.setPassword(password); // Deberías cifrar la contraseña aquí.

        persistenceController.addUser(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @Setter
    @Getter
    // Clase DTO para la solicitud de registro
    public static class SignupRequest {
        private String username;
        private String password;
        private String confirmPassword;
    }
}
