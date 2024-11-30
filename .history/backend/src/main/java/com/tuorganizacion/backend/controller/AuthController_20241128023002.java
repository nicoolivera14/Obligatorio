package com.tuorganizacion.backend.controller;

import com.tuorganizacion.backend.model.Usuarios;
import com.tuorganizacion.backend.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public ResponseEntity<?> login(@RequestBody Usuarios request) {
        Optional<Usuarios> usuario = userRepository.findByUsername(request.getUsername());

        if (usuario.isPresent() && usuario.get().getPassword() == passwordEncoder.encode(request.getPassword())) {
            return ResponseEntity.ok().body("Login successful");
        } 

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña inválidos.");
    }

    @PostMapping("/singup")
    public ResponseEntity<?> singup(@RequestBody Usuarios usuario) {
        if (userRepository.findByUsername(usuario.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario ya existe.");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        userRepository.save(usuario);

        return ResponseEntity.ok().body("Usuario guardado correctamente.");
    }
}