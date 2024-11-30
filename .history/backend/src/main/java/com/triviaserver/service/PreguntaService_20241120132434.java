package com.tuorganizacion.backend.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PreguntaService {
    private static final String API_URL = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "TU_API_KEY";

    public JsonNode obtenerPreguntasDeAPI() throws IOException, InterruptedException {
        String prompt = "Genera un JSON con 30 preguntas de trivia. Cada pregunta debe tener el siguiente formato:\n"
                        + "{\n"
                        + "  \"pregunta\": \"Texto de la pregunta\",\n"
                        + "  \"opciones\": [\"Opción 1\", \"Opción 2\", \"Opción 3\", \"Opción 4\"],\n"
                        + "  \"respuestaCorrecta\": \"Texto de la opción correcta\"\n"
                        + "}\n"
                        + "El contenido de las preguntas debe ser variado y abarcando temas generales.";

        String requestBody = String.format(
            "{ \"model\": \"text-davinci-003\", " +
            "\"prompt\": \"%s\", " +
            "\"max_tokens\": 500, " +
            "\"temperature\": 0.7, " +
            "\"top_p\": 1.0, " +
            "\"frequency_penalty\": 0.0, " +
            "\"presence_penalty\": 0.0 }",
            prompt.replace("\n", "\\n").replace("\"", "\\\"")
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + API_KEY)
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Convertir la respuesta JSON a un objeto Java
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response.body());
    }
}
