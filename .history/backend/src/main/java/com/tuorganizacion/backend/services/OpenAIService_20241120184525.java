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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.backend.model.GPTRequest;
import com.tuorganizacion.backend.model.GPTResponse;

@Service
public class OpenAIService {

    //@Value("${openai.api.key}")

    private String apiKey = "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";

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
        headers.setBearerAuth(apiKey);

        HttpEntity<GPTRequest> entity = new HttpEntity<GPTRequest>(request, headers);
        ResponseEntity<String> respuesta = restTemplate.postForEntity(apiUrl, entity, String.class);

        if (respuesta.getStatusCode() == HttpStatus.OK) {
            GPTResponse gptResponse = objectMapper.readValue(respuesta.getBody(), GPTResponse.class)
        } else {
            throw new RuntimeException("Error: " + respuesta.getStatusCode() + " - " + respuesta.getBody());
        
        }
    }
}