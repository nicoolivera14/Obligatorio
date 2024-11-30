package com.tuorganizacion.backend.controller;

import com.tuorganizacion.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        if (userService.createUser(username, password)) {
            return "User registered successfully!";
        } else {
            return "User already exists!";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        if (userService.authenticate(username, password)) {
            return "Login successful!";
        } else {
            return "Invalid username or password.";
        }
    }
}
