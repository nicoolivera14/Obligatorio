package com.tuorganizacion.backend.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.tuorganizacion.backend.model.Pregunta;
import com.tuorganizacion.backend.services.PreguntaService;


@RestController
@RequestMapping("/preguntas")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    @GetMapping("/generar")
    public ResponseEntity<List<Pregunta>> generarPreguntas(@RequestParam int cantPreguntas) {
        try {
            List<Pregunta> preguntas = preguntaService.obtenerPreguntasDeAPI(cantPreguntas);
            return ResponseEntity.ok(preguntas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null)
        }
    }
}
/*@RestController
@RequestMapping("/partida")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;
    Pregunta pregunta;

    @GetMapping("/iniciar")
    public ResponseEntity<String> iniciarPartida() {
        try {
            pregunta = preguntaService.obtenerPreguntasDeAPI(); 
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
    }
    @GetMapping("/siguiente-pregunta")
    public ResponseEntity<Pregunta> siguientePregunta() {

        return ResponseEntity.ok(pregunta);
    }

    
    /*@PostMapping("/validar-respuesta")
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
}*/