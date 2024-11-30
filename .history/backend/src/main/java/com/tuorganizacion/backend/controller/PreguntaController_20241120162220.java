package com.triviaserver.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.triviaserver.model.Pregunta;
import com.triviaserver.service.PreguntaService;

@RestController
@RequestMapping("/partida")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    
    private List<Pregunta> preguntasPartida = new ArrayList<>();
    private int preguntaActual = 0;

    // Endpoint para iniciar una nueva partida
    @GetMapping("/iniciar")
    public ResponseEntity<String> iniciarPartida() {
        try {
            preguntasPartida = preguntaService.obtenerPreguntasDeAPI();
            preguntaActual = 0; // Reiniciar el progreso
            return ResponseEntity.ok("Partida iniciada. Usa el endpoint '/siguiente-pregunta' para comenzar.");
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error al obtener preguntas de la API.");
        }
    }

    // Endpoint para obtener la siguiente pregunta
    @GetMapping("/siguiente-pregunta")
    public ResponseEntity<Pregunta> siguientePregunta() {
        if (preguntaActual >= preguntasPartida.size()) {
            return ResponseEntity.ok(null); // Todas las preguntas respondidas
        }
        Pregunta pregunta = preguntasPartida.get(preguntaActual);
        return ResponseEntity.ok(pregunta);
    }

    // Endpoint para validar una respuesta
    @PostMapping("/validar-respuesta")
    public ResponseEntity<String> validarRespuesta(@RequestParam String respuestaUsuario) {
        if (preguntaActual >= preguntasPartida.size()) {
            return ResponseEntity.ok("La partida ya terminó.");
        }

        Pregunta pregunta = preguntasPartida.get(preguntaActual);

        boolean esCorrecta = respuestaUsuario.equalsIgnoreCase(pregunta.getRespuestaCorrecta());
        preguntaActual++; // Pasar a la siguiente pregunta

        if (esCorrecta) {
            return ResponseEntity.ok("Respuesta correcta. La próxima pregunta llegará en 5 segundos.");
        } else {
            return ResponseEntity.ok("Respuesta incorrecta. La próxima pregunta llegará en 5 segundos.");
        }
    }
}
