package com.triviaserver.controller;

import com.triviaserver.model.Pregunta;
import com.triviaserver.service.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    @GetMapping("/iniciar-partida")
    public ResponseEntity<List<Pregunta>> iniciarPartida() {
        try {
            List<Pregunta> preguntas = preguntaService.obtenerPreguntasDeAPI();
            
            // Limitar la lista a las primeras 10 preguntas
            List<Pregunta> preguntasLimitadas = preguntas.subList(0, Math.min(10, preguntas.size()));
            
            return ResponseEntity.ok(preguntasLimitadas);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
