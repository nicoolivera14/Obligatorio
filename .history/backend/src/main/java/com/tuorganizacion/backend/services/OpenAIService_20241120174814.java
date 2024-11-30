package com.tuorganizacion.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    @Value ("${openai.api.key}")
    private String apiKey;
}