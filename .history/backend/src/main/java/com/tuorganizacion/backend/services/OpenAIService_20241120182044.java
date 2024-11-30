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

import com.tuorganizacion.backend.model.GPTRequest;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public OpenAIService (RestTemplate restTemplate){
        this.restTemplate = restTemplate;

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
                return respuesta.getBody();
        } else {
            throw new RuntimeException("Error: " + respuesta.getStatusCode() + " - " + respuesta.getBody());
        
        }
    }
}