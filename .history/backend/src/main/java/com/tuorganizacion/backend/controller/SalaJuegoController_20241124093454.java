package com.tuorganizacion.backend.controller;

import com.tuorganizacion.backend.model.Pregunta;
import com.tuorganizacion.backend.services.PreguntaService;
import com.tuorganizacion.backend.model.SalaJuego;
import com.tuorganizacion.backend.services.SalaJuegoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sala")
public class SalaJuegoController {

    @Autowired
    private PreguntaService preguntaService; 
    @Autowired
    private SalaJuegoService salaJuegoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearSalaJuego(@RequestParam int cantPreguntas) {
        try {
            List<Pregunta> preguntas = preguntaService.obtenerPreguntasDeAPI(cantPreguntas);
            
            String salaId = UUID.randomUUID().toString();
            SalaJuego nuevaSala = new SalaJuego(salaId, preguntas);

            return ResponseEntity.ok(nuevaSala);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear la sala: " + e.getMessage());
        }
    }

    @PostMapping("/{idSala}/unirse")
    public ResponseEntity<String> unirseASalaJuego(@PathVariable String idSala, @RequestParam String jugador) {
        boolean unido = salaJuegoService.unirseASalaJuego(idSala, jugador);
        if (unido) {
            return ResponseEntity.ok("Jugador unido a la sala.");
        } else {
            return ResponseEntity.status(404).body("Sala no encontrada o jugador ya está en la sala.");
        }
    }

    @GetMapping("/{idSala}/pregunta")
    public ResponseEntity<Pregunta> obtenerPregunta(@PathVariable String idSala) {
        SalaJuego sala = salaJuegoService.obtenerSalaJuego(idSala);
        if (sala != null) {
            Pregunta pregunta = sala.getPreguntas().get(sala.getPreguntaActual());
            return ResponseEntity.ok(pregunta);
        }
        return ResponseEntity.status(404).body(null);
    }
}
