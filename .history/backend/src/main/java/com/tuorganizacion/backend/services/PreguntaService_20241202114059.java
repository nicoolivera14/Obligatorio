package com.tuorganizacion.backend.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.backend.model.Constantes;
import com.tuorganizacion.backend.model.Pregunta;
import com.tuorganizacion.backend.model.PreguntaDTO;

@Service
public class PreguntaService {
    private final OpenAIService openAIService;

    public PreguntaService(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    public List<Pregunta> obtenerPreguntasDeAPI(int cantPreguntas) throws IOException, InterruptedException {
    String prompt = String.format("""
        Por favor, genera %d preguntas de trivia, con las categorías: %s.
        Cada pregunta debe tener los siguientes campos en JSON:
        [
          {
            "textoPregunta": "Texto de la pregunta",
            "categoria": "Categoría",
            "opciones": ["Opción 1", "Opción 2", "Opción 3", "Opción 4"],
            "respuestaCorrecta": Índice de la respuesta correcta (0-3)
          }
        ]
        """, cantPreguntas, Constantes.CATEGORIAS.toString());

    String preguntasJson = openAIService.consultaGPT(prompt);

    // Convertir el JSON a una lista de DTOs
    ObjectMapper mapper = new ObjectMapper();
    List<PreguntaDTO> preguntasDTO = mapper.readValue(preguntasJson, new TypeReference<>() {});

    // Convertir DTOs a entidades
    return preguntasDTO.stream()
                       .map(this::convertirDTOaEntidad)
                       .collect(Collectors.toList());
}
}
    /*public List<Pregunta> obtenerPreguntasDeAPI(int cantPreguntas) throws IOException, InterruptedException {
        String prompt = String.format("""
        Por favor, genera %d preguntas de trivia, a su vez debe ser de las categorias de esta variable: %s. EN FORMATO JSON. Cada pregunta debe tener los siguientes campos:
        [
  {
  "textoPregunta": "Texto de la pregunta",
            "categoria": "Categoría",
            "opciones": ["textoOpcion 1", "textoOpcion 2", "textoOpcion 3", "textoOpcion 4"],
            "respuestaCorrecta": Índice de la respuesta correcta (0-3)
}
,
  {
  "textoPregunta": "Texto de la pregunta",
            "categoria": "Categoría",
            "opciones": ["textoOpcion 1", "textoOpcion 2", "textoOpcion 3", "textoOpcion 4"],
            "respuestaCorrecta": Índice de la respuesta correcta (0-3)
}
]

         
        """, cantPreguntas, Constantes.CATEGORIAS.toString());

        String preguntasJson = openAIService.consultaGPT(prompt);
                        
        // Convertir el JSON en un objeto Pregunta
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(preguntasJson,
         new TypeReference<List<Pregunta>>() {}
         );
         
    }
}*/
