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
    Por favor, genera %d preguntas de trivia, en formato JSON. Cada pregunta debe tener los siguientes campos:
    {
        "textoPregunta": "Texto de la pregunta",
        "PreguntaOpcion": ["Opción 1", "Opción 2", "Opción 3", "Opción 4"],
        "respuestaCorrecta": "Índice de la opción correcta",
        "categoria": "Categoría"
    }
    Asegúrate de que la respuesta esté bien formateada como un array de objetos JSON, sin números o puntos antes de las preguntas. Ejemplo:
    [
        {
            "textoPregunta": "Pregunta 1",
            "PreguntaOpcion": ["Opción 1", "Opción 2", "Opción 3", "Opción 4"],
            "respuestaCorrecta": "Índice de la opción correcta",
            "categoria": "Categoría"
        },
        {
            "textoPregunta": "Pregunta 2",
            "PreguntaOpcion": ["Opción 1", "Opción 2", "Opción 3", "Opción 4"],
            "respuestaCorrecta": "Índice de la opción correcta",
            "categoria": "Categoría"
        }
    ]
    """
, cantPreguntas, Constantes.CATEGORIAS.toString());

        String preguntasJson = openAIService.consultaGPT(prompt);
        System.out.println("Respuesta completa de OpenAI: " + preguntasJson);
        
                        
        // Convertir el JSON en un objeto Pregunta
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(preguntasJson,
         new TypeReference<List<Pregunta>>() {}
         );
        
    }
}

