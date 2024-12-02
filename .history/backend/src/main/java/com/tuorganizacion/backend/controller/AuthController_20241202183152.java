package com.tuorganizacion.backend.controller;

import com.tuorganizacion.backend.model.User;
import com.tuorganizacion.backend.repository.UserRepository;
import com.tuorganizacion.backend.security.JwtUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
      );
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = jwtUtil.generateToken(authRequest.getUsername());
    return ResponseEntity.ok(Map.of("token", token));
  }

  @PostMapping("/signup")
  public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Username already exists"));
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);

    String token = jwtUtil.generateToken(user.getUsername());
    return ResponseEntity.ok(Map.of("token", token));
  }

  @Getter
  @Setter
  public static class AuthRequest {
    private String username;
    private String password;
  }
}
