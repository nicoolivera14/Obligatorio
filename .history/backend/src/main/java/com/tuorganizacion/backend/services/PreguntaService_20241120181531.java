package com.tuorganizacion.backend.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.backend.model.Pregunta;

@Service
public class PreguntaService {
    private final OpenAIService openAIService;

    public List<Pregunta> obtenerPreguntasDeAPI() throws IOException, InterruptedException {
        String prompt = """
                        Genera un JSON con 10 preguntas de trivia. Cada pregunta debe tener el siguiente formato:
                        {
                          "pregunta": "Texto de la pregunta",
                          "opciones": ["Opción 1", "Opción 2", "Opción 3", "Opción 4"],
                          "respuestaCorrecta": "Texto de la opción correcta"
                        }
                        El contenido de las preguntas debe ser variado y abarcando temas generales.
                        """;

        

        // Convertir el JSON de respuesta a una lista de objetos Pregunta
        ObjectMapper mapper = new ObjectMapper();

        // Asumimos que las preguntas están en el nodo "choices" y son un JSON válido
        String preguntasJson = openAIService.consultaGPT(prompt);

        // Convertir el texto JSON en una lista de preguntas
        return mapper.readValue(
            preguntasJson,
            mapper.getTypeFactory().constructCollectionType(List.class, Pregunta.class)
        );
    }
}

