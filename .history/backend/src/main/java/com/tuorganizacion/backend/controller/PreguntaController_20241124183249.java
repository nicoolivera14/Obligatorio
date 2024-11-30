package com.tuorganizacion.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.status(500).body(null);
        }
    }
}