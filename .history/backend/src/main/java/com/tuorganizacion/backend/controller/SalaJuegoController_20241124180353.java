package com.tuorganizacion.backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tuorganizacion.backend.model.Pregunta;
import com.tuorganizacion.backend.model.SalaJuego;
import com.tuorganizacion.backend.services.PreguntaService;
import com.tuorganizacion.backend.services.SalaJuegoService;

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

    @GetMapping("/{idSala}/siguiente-pregunta")
    public ResponseEntity<?> obtenerPreguntaNoRespondida(@PathVariable String idSala) {
        Pregunta pregunta = salaJuegoService.obtenerPreguntaNoRespondida(idSala);
        
        if (pregunta != null) {
            return ResponseEntity.ok(pregunta);
        } 
        
        if (salaJuegoService.obtenerSalaJuego(idSala) != null) {
            return ResponseEntity.status(204).body("No hay más preguntas disponibles.");
        }
    
    return ResponseEntity.status(404).body("Sala no encontrada.");
    }

    @PostMapping("/{idSala}/responder-pregunta")
    public ResponseEntity<?> responderPregunta(
        @PathVariable String idSala,
        @RequestParam int indicePregunta) {
        boolean marcada = salaJuegoService.marcarPreguntaComoRespondida(idSala, indicePregunta);

        if (marcada) {
            return ResponseEntity.ok("Pregunta marcada como respondida.");
        } else {
            return ResponseEntity.status(400).body("No se pudo marcar la pregunta como respondida. Verifica el ID de la sala o el índice de la pregunta.");
    }
}


}