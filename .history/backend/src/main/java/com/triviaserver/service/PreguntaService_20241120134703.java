package com.triviaserver.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triviaserver.model.Pregunta;

@Service
public class PreguntaService {
    private static final String API_URL = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "TU_API_KEY";

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

        // Convertir el JSON de respuesta a una lista de objetos Pregunta
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.body());

        // Asumimos que el JSON está contenido en un nodo llamado "choices" y que el texto es un JSON válido
        String preguntasJson = rootNode.get("choices").get(0).get("text").asText();

        // Convertir el texto de preguntas al formato List<Pregunta>
        return mapper.readValue(
            preguntasJson,
            mapper.getTypeFactory().constructCollectionType(List.class, Pregunta.class)
        );
    }
}
