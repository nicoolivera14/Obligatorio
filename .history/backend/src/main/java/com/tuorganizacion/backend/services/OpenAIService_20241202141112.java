package com.tuorganizacion.backend.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.backend.model.GPTRequest;
import com.tuorganizacion.backend.model.GPTResponse;


@Service
public class OpenAIService {
    @Value("${openai.api.key}")
    private final String apikey;
    //private final String apiKey = "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";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OpenAIService (RestTemplate restTemplate, ObjectMapper objectMapper){
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

    }

    public String consultaGPT (String prompt) {
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        GPTRequest request = new GPTRequest();
        request.setModel("gpt-3.5-turbo");
        request.setMessages(Collections.singletonList(new GPTRequest.Message("user", prompt)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apikey);

        HttpEntity<GPTRequest> entity = new HttpEntity<GPTRequest>(request, headers);
        ResponseEntity<String> respuesta = restTemplate.postForEntity(apiUrl, entity, String.class);

        if (respuesta.getStatusCode() == HttpStatus.OK) {
            try {
            GPTResponse gptResponse = objectMapper.readValue(respuesta.getBody(), GPTResponse.class);

            if (gptResponse.getChoices() != null && !gptResponse.getChoices().isEmpty()) {
                return gptResponse.getChoices().get(0).getMessage().getContent();
            } else {
                throw new RuntimeException("No se encontraron opciones.");
            }
            } catch (Exception e) {
                throw new RuntimeException("Error parseando respuesta: " + e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("Error: " + respuesta.getStatusCode() + " - " + respuesta.getBody());
        
        }
    }
}