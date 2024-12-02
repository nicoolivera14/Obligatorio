package com.tuorganizacion.backend.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


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
            "opciones": ["Opción 1", "Opción 2", "Opción 3", "Opción 4"],
            "respuestaCorrecta": "Índice de la opción correcta",
            "categoria": "Categoría"
        } 
        """, cantPreguntas, Constantes.CATEGORIAS.toString());

        String preguntasJson = openAIService.consultaGPT(prompt);
        System.out.println("Respuesta completa de OpenAI: " + preguntasJson);
        
                        
        // Convertir el JSON en un objeto Pregunta
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(preguntasJson,
         new TypeReference<List<Pregunta>>() {}
         );
        List<Pregunta> preguntas = map.entrySet().stream()
        .map(entry -> {
            Map<String, Object> preguntaData = entry.getValue();
            Pregunta pregunta = new Pregunta();
            pregunta.setTextoPregunta((String) preguntaData.get("textoPregunta"));
            pregunta.setOpciones((List<String>) preguntaData.get("opciones"));
            pregunta.setRespuestaCorrecta((String) preguntaData.get("respuestaCorrecta"));
            pregunta.setCategoria((String) preguntaData.get("categoria"));
            return pregunta;
        })
        .collect(Collectors.toList());

    }
}

