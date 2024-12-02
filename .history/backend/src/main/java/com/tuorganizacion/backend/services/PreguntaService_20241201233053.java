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

    public List<Pregunta> obtenerPreguntasDeAPI(int cantPreguntas, Constantes.CATEGORIAS.TO) throws IOException, InterruptedException {
        String prompt = String.format("""
            [
                {
                    "textoPregunta": "Texto de la pregunta",
                    "PreguntaOpcion": ["Opción 1", "Opción 2", "Opción 3", "Opción 4"],
                    "respuestaCorrecta": "Indice de la opción correcta",
                    "categoria": "Nombre de la categoría"
                },
                ...
            ]
            El contenido de las preguntas debe ser variado y abarcando las siguientes categorias: %s.
            """, cantPreguntas, Constantes.CATEGORIAS.toString());

        String preguntasJson = openAIService.consultaGPT(prompt);
        System.out.println("Respuesta completa de OpenAI: " + preguntasJson);
        
                        
        // Convertir el JSON en un objeto Pregunta
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(preguntasJson,
         new TypeReference<List<Pregunta>>() {}
         );
        
    }
}

