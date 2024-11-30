package com.tuorganizacion.backend.controller;


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

import com.tuorganizacion.backend.model.Pregunta;
import com.tuorganizacion.backend.services.PreguntaService;

@RestController
@RequestMapping("/partida")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;
    String prueba;
    
    private List<Pregunta> preguntasPartida = new ArrayList<>();
    private int preguntaActual = 0;

    @GetMapping("/iniciar")
    public ResponseEntity<String> iniciarPartida() {
        try {
            prueba = preguntaService.obtenerPreguntasDeAPI();
            //preguntasPartida = preguntaService.obtenerPreguntasDeAPI();
            preguntaActual = 0; 
            return ResponseEntity.ok("Partida iniciada. Usa el endpoint '/siguiente-pregunta' para comenzar.");
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error al obtener preguntas de la API.");
        }
    }

    // Endpoint para obtener la siguiente pregunta
    /*@GetMapping("/siguiente-pregunta")
    public ResponseEntity<Pregunta> siguientePregunta() {
        if (preguntaActual >= preguntasPartida.size()) {
            return ResponseEntity.ok(null); // Todas las preguntas respondidas
        }
        Pregunta pregunta = preguntasPartida.get(preguntaActual);
        return ResponseEntity.ok(pregunta);
    }*/
    @GetMapping("/siguiente-pregunta")
    public ResponseEntity<String> siguientePregunta() {

        return ResponseEntity.ok(prueba);
    }

    
    @PostMapping("/validar-respuesta")
    public ResponseEntity<String> validarRespuesta(@RequestParam String respuestaUsuario) {
        if (preguntaActual >= preguntasPartida.size()) {
            return ResponseEntity.ok("La partida ya terminó.");
        }

        Pregunta pregunta = preguntasPartida.get(preguntaActual);

        boolean esCorrecta = respuestaUsuario.equalsIgnoreCase(pregunta.getRespuestaCorrecta());
        preguntaActual++; 

        if (esCorrecta) {
            return ResponseEntity.ok("Respuesta correcta. La próxima pregunta llegará en 5 segundos.");
        } else {
            return ResponseEntity.ok("Respuesta incorrecta. La próxima pregunta llegará en 5 segundos.");
        }
    }
}
