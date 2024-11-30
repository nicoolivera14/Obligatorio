package com.tuorganizacion.backend.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.backend.model.Pregunta;

@Service
public class PreguntaService {
    private final OpenAIService openAIService;

    public PreguntaService(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    public Pregunta obtenerPreguntasDeAPI() throws IOException, InterruptedException {
        String prompt = String.format("""
                        Genera un JSON con %d pregunta de trivia. La pregunta debe tener el siguiente formato:
                        {
                          "pregunta": "Texto de la pregunta",
                          "opciones": ["Opción 1", "Opción 2", "Opción 3", "Opción 4"],
                          "respuestaCorrecta": "Texto de la opción correcta"
                        }
                        El contenido de las preguntas debe ser variado y abarcando temas generales.
                        """, cantPreguntas);

        

                        String preguntasJson = openAIService.consultaGPT(prompt);
                        
                        
                        
                        // Convertir el JSON en un objeto Pregunta
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(preguntasJson, Pregunta.class);
        
    }
}

