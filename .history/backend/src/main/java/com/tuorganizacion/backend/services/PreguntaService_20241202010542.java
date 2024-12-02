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
        Por favor, genera %d preguntas de trivia, a su vez debe ser de las categorias de esta variable: %s. EN FORMATO JSON. Cada pregunta debe tener los siguientes campos:
        Si hay más de dos preguntas, separar con una coma los arrays.
        [
  {
    "textoPregunta": "pregunta",
    "opciones": ["opción 1", "opción 2", "opción 3"],
    "respuestaCorrecta": 0,
    "categoria": "General"
  },
  {
    "textoPregunta": "¿Pregunta 2?",
    "opciones": ["opción 1", "opción 2"],
    "respuestaCorrecta": 1,
    "categoria": "Historia"
  }
]

         
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

