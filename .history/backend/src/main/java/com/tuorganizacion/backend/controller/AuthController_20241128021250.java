package com.tuorganizacion.backend.controller;

import com.tuorganizacion.backend.model.Usuarios;
import com.tuorganizacion.persistence.UserRepository;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SolicitudLogin request) {
        Optional<Usuarios> usuario = userRepository.findByUsernameAndPassword(
            request.getUsername(), 
            passwordEncoder.encode(request.password)
        );

        if (usuario.isPresent()) {
            return ResponseEntity.ok().body("Login successful");
        } 

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    }

    @Getter
    @Setter
    public static class SolicitudLogin {
        private String username;
        private String password;  
    }
}