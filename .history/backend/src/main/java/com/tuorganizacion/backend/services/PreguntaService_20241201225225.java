package com.tuorganizacion.backend.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.backend.model.Constantes;
import com.tuorganizacion.backend.model.Pregunta;

@Service
public class PreguntaService {
    private final OpenAIService openAIService;

    public PreguntaService(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    public List<Pregunta> obtenerPreguntasDeAPI(int cantPreguntas) throws IOException, InterruptedException {
        String prompt = String.format("""
            Genera un JSON con %d preguntas de trivia. Cada pregunta debe tener el siguiente formato:
            [
                {
              "textoPregunta": "Texto de la pregunta",
              "opciones": ["Opción 1", "Opción 2", "Opción 3", "Opción 4"],
              "respuestaCorrecta": "Indice de la opción correcta",
              "categoria": "categoria"
                },
                ...
                ]
            El contenido de las preguntas debe ser variado y abarcando las siguientes categorias: %s.
            """, cantPreguntas, Constantes.CATEGORIAS.toString());

                        String preguntasJson = openAIService.consultaGPT(prompt);
                     
                        // Convertir el JSON en un objeto Pregunta
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(preguntasJson,
         new TypeReference<List<Pregunta>>() {}
         );
        
    }
}

