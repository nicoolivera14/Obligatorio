package com.tuorganizacion.backend.controller;
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
        String name = signupRequest.getName();
        String password = signupRequest.getPassword();
        String confirmPassword = signupRequest.getConfirmPassword();

        List<User> users = persistenceController.getUsersList();
        if (users == null) {
            return ResponseEntity.badRequest().body("Database error: Unable to retrieve user list.");
        }

        // Password validation
        if (password.length() < 8 || !password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Password must be at least 8 characters and match the confirmation.");
        }

        // Create new user
        User user = new User();
        user.setName(name);
        user.setPassword(password); // Deberías cifrar la contraseña aquí.

        persistenceController.addUser(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    // Clase DTO para la solicitud de registro
    public static class SignupRequest {
        private String name;
        private String password;
        private String confirmPassword;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
    }
}
