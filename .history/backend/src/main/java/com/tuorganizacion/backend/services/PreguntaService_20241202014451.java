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
        [
  {
  "textoPregunta": "texto de la pregunta",
  "opciones": [
    {
      "textoOpcion": "opcion 1"
    },
    {
      "textoOpcion": "opcion 2"
    },
    {
      "textoOpcion": "opcion 3"
    },
    {
      "textoOpcion": "opcion 4"
    }
  ],
  "respuestaCorrecta": "indice de respuesta correcta",
  "categoria": "categoria"
}
,
  {
  "textoPregunta": "texto de la pregunta",
  "opciones": [
    {
      "textoOpcion": "opcion 1"
    },
    {
      "textoOpcion": "opcion 2"
    },
    {
      "textoOpcion": "opcion 3"
    },
    {
      "textoOpcion": "opcion 4"
    }
  ],
  "respuestaCorrecta": "indice de respuesta correcta",
  "categoria": "categoria"
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
}

