package com.tuorganizacion.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuorganizacion.backend.model.Usuario;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder) {
        //this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario request) {
        /*Optional<Usuarios> usuario = userRepository.findByUsername(request.getUsername());

        if (usuario.isPresent() && usuario.get().getPassword() == passwordEncoder.encode(request.getPassword())) {
            return ResponseEntity.ok().body("Login successful");
        }*/

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña inválidos.");
    }

    @PostMapping("/singup")
    public ResponseEntity<?> singup(@RequestBody Usuario usuario) {
        /*if (userRepository.findByUsername(usuario.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario ya existe.");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        userRepository.save(usuario);*/

        return ResponseEntity.ok().body("Usuario guardado correctamente.");
    }
}