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

    public String obtenerPreguntasDeAPI() throws IOException, InterruptedException {
        String prompt = """
                        Genera un JSON con 3 preguntas de trivia. Cada pregunta debe tener el siguiente formato:
                        {
                          "pregunta": "Texto de la pregunta",
                          "opciones": ["Opción 1", "Opción 2", "Opción 3", "Opción 4"],
                          "respuestaCorrecta": "Texto de la opción correcta"
                        }
                        El contenido de las preguntas debe ser variado y abarcando temas generales.
                        """;

        

        // Convertir el JSON de respuesta a una lista de objetos Pregunta
        ObjectMapper mapper = new ObjectMapper();

        
        String preguntasJson = openAIService.consultaGPT(prompt);

        // Convertir el texto JSON en una lista de preguntas
        /*return mapper.readValue(
            preguntasJson,
            mapper.getTypeFactory().constructCollectionType(List.class, Pregunta.class)
        );*/
        return preguntasJson;
    }
}

