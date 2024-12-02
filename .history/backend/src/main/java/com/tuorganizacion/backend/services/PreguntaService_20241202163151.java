package com.tuorganizacion.backend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.backend.model.Constantes;
import com.tuorganizacion.backend.model.Pregunta;

@Service
public class PreguntaService {
    private final OpenAIService openAIService;

    public PreguntaService(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    public List<Pregunta> obtenerPreguntasDeAPI(int cantPreguntas) throws IOException, InterruptedException {
        // Construir el prompt para OpenAI
        String prompt = String.format("""
        Por favor, genera %d preguntas de trivia. Las categorías disponibles son: %s. 
        Devuelve las preguntas en el siguiente formato JSON:
        [
            {
                "textoPregunta": "Texto de la pregunta",
                "categoria": "categoria",
                "opciones": ["textoOpcion1", "textoOpcion2", "textoOpcion3", "textoOpcion4"],
                "respuestaCorrecta": Índice de la respuesta correcta (0-3)
            }
        ]
        """, cantPreguntas, Constantes.CATEGORIAS.toString());

        // Realizar la consulta a la API
        String preguntasJson = openAIService.consultaGPT(prompt);

        // Convertir el JSON a una lista de objetos Pregunta
        ObjectMapper mapper = new ObjectMapper();
        List<Pregunta> preguntas;
        try {
            preguntas = mapper.readValue(preguntasJson, new TypeReference<List<Pregunta>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el JSON a la lista de preguntas: " + e.getMessage(), e);
        }

        // Validar que se hayan recibido preguntas
        if (preguntas == null || preguntas.isEmpty()) {
            throw new RuntimeException("La API de OpenAI no devolvió preguntas.");
        }

        // Marcar todas las preguntas como no respondidas
        preguntas.forEach(pregunta -> pregunta.setRespondida(false));
        return preguntas;
    }
}

