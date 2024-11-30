package com.tuorganizacion.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/") // Ruta raíz
    public String home() {
        return "Bienvenido a la aplicación backend";
    }
}
