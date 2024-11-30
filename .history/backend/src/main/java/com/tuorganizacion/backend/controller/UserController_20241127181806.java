package com.tuorganizacion.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tuorganizacion.backend.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
public String registerUser(@RequestParam String username, @RequestParam String password) {
    boolean userCreated = userService.createUser(username, password);
    if (userCreated) {
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
