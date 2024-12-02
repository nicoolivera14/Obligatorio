package com.tuorganizacion.backend.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tuorganizacion.backend.model.Constantes;
import com.tuorganizacion.backend.model.Pregunta;
import com.tuorganizacion.backend.model.SalaJuego;
import com.tuorganizacion.backend.model.SalaRequest;
import com.tuorganizacion.backend.model.Usuario;
import com.tuorganizacion.backend.repository.SalaJuegoRepository;
import com.tuorganizacion.backend.repository.UserRepository;
import com.tuorganizacion.backend.services.PreguntaService;
import com.tuorganizacion.backend.services.SalaJuegoService;


@RestController
@RequestMapping("/sala")
public class SalaJuegoController {

    @Autowired
    private PreguntaService preguntaService; 
    @Autowired
    private SalaJuegoService salaJuegoService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SalaJuegoRepository salaJuegoRepository;

    private static final Logger logger = LoggerFactory.getLogger(SalaController.class);

    @GetMapping("/categorias")
    public ResponseEntity<List<String>> categorias() {
        return ResponseEntity.ok(Constantes.CATEGORIAS);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearSalaJuego(@RequestBody SalaRequest salaRequest) {
        try {
            logger.info("Recibiendo solicitud para crear sala con host: {}", salaRequest.getHostUsername());
            
            // Buscar el usuario
            Usuario usuario = userRepository.findByUsername(salaRequest.getHostUsername())
                .orElseThrow(() -> {
                    logger.error("Usuario no encontrado: {}", salaRequest.getHostUsername());
                    return new RuntimeException("Usuario no encontrado");
                });
            
            logger.info("Usuario encontrado: {}", usuario.getUsername());

            // Obtener preguntas de la API
            List<Pregunta> preguntas = preguntaService.obtenerPreguntasDeAPI(salaRequest.getCantPreguntas());
            logger.info("Preguntas obtenidas de la API: {}", preguntas.size());

            // Crear la sala
            SalaJuego salaJuego = new SalaJuego();
            salaJuego.setIdSalaJuego(UUID.randomUUID().toString());
            salaJuego.setPreguntas(preguntas);
            salaJuego.getJugadores().add(usuario);

            logger.info("Guardando la sala con ID: {}", salaJuego.getIdSalaJuego());

            // Guardar la sala en la base de datos
            SalaJuego salaGuardada = salaJuegoRepository.save(salaJuego);
            logger.info("Sala guardada correctamente con ID: {}", salaGuardada.getIdSalaJuego());

            return ResponseEntity.ok(salaGuardada);
        } catch (RuntimeException e) {
            logger.error("Error en la creación de la sala: {}", e.getMessage(), e);
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al crear la sala: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error al crear la sala: " + e.getMessage());
        }
    }

    @PostMapping("/{idSala}/unirse")
    public ResponseEntity<String> unirseASalaJuego(@PathVariable String idSala, @RequestParam Usuario jugador) {
        boolean unido = salaJuegoService.unirseASalaJuego(idSala, jugador);
        if (unido) {
            return ResponseEntity.ok("Jugador unido a la sala.");
        } else {
            return ResponseEntity.status(404).body("Sala no encontrada o jugador ya está en la sala.");
        }
    }

    @GetMapping("/{idSala}/siguiente-pregunta")
    public ResponseEntity<?> obtenerPreguntaNoRespondida(@PathVariable String idSala, @RequestParam int idUsuario) {
            SalaJuego salaJuego = salaJuegoService.obtenerSalaJuego(idSala);
            Pregunta pregunta = salaJuegoService.obtenerPreguntaNoRespondida(idSala);

            if (salaJuego.getJugadores().isEmpty() || salaJuego.getJugadores().get(0).getIdUsuario() != idUsuario) {
                return ResponseEntity.status(403).body("Solo el host puede solicitar la siguiente pregunta.");
            }

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
        @RequestParam int idPregunta,
        @RequestParam int respuestaUsuario) {
    
        SalaJuego sala = salaJuegoService.obtenerSalaJuego(idSala);

        if (sala == null) {
            return ResponseEntity.status(404).body("Sala no encontrada.");
        }
    
        if (idPregunta < 0 || idPregunta >= sala.getPreguntas().size()) {
            return ResponseEntity.status(400).body("El índice de la pregunta es inválido.");
        }

        Pregunta pregunta = sala.getPreguntas().get(idPregunta);

        if (pregunta.isRespondida()) {
            return ResponseEntity.status(400).body("La pregunta ya ha sido respondida.");
        }
    
        boolean esCorrecta = respuestaUsuario == pregunta.getRespuestaCorrecta();
    
        pregunta.setRespondida(true);
    
        if (esCorrecta) {
            return ResponseEntity.ok("Respuesta correcta. La próxima pregunta llegará en 5 segundos.");
        } else {
            return ResponseEntity.ok("Respuesta incorrecta. La próxima pregunta llegará en 5 segundos.");
        }
    }
    @PostMapping("/{idSala}/limpiar")
    public ResponseEntity<String> limpiarSala(@PathVariable String idSala) {
        boolean salaLimpia = salaJuegoService.limpiarSalaSiTermino(idSala);
        if (salaLimpia) {
            return ResponseEntity.ok("Sala limpiada correctamente.");
        }
        return ResponseEntity.status(404).body("Sala no encontrada o aún hay preguntas pendientes.");
    }

}



