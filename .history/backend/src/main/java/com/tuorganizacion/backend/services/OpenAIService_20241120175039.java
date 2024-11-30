package com.tuorganizacion.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    @Value ("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public OpenAIService (RestTemplate restTemplate){
        this.restTemplate = restTemplate;

    }
}