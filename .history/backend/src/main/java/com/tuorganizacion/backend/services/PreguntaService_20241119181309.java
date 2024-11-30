package com.triviaserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triviaserver.model.Pregunta;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class PreguntaService {
    private static final String API_URL = "URL_DE_LA_API";
    private static final String API_KEY = "TU_API_KEY";

    public List<Pregunta> obtenerPreguntasDeAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Authorization", "Bearer " + API_KEY)
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(response.body(), Pregunta[].class));
    }
}
