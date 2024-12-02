package com.tuorganizacion.backend.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.backend.model.Constantes;
import com.tuorganizacion.backend.model.Pregunta;
import com.tuorganizacion.backend.model.PreguntaOpcion;

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
            "opciones": ["textoOpcion 1", "textoOpcion 2", "textoOpcion 3", "textoOpcion 4"],
            "respuestaCorrecta": "Índice de la opción correcta",
            "categoria": "Categoría"
        }
         
        """, cantPreguntas, Constantes.CATEGORIAS.toString());

        String preguntasJson = openAIService.consultaGPT(prompt);
        System.out.println("Respuesta completa de OpenAI: " + preguntasJson);
        
                        
        // Convertir el JSON en un mapa con las preguntas (ya que no está en una lista)
    ObjectMapper mapper = new ObjectMapper();
    // Deserializar el JSON como un mapa (esto es diferente de un ArrayList)
    Map<String, Map<String, Object>> map = mapper.readValue(preguntasJson, new TypeReference<Map<String, Map<String, Object>>>() {});
        
    
    // Convertir las preguntas del mapa a una lista
    List<Pregunta> preguntas = map.values().stream()
        .map(entry -> {
            Pregunta pregunta = new Pregunta();
            pregunta.setTextoPregunta((String) entry.get("textoPregunta"));
            pregunta.setOpciones((List<PreguntaOpcion>) entry.get("opciones"));
            pregunta.setRespuestaCorrecta((Integer) entry.get("respuestaCorrecta"));
            pregunta.setCategoria((String) entry.get("categoria"));
            return pregunta;
        })
        .collect(Collectors.toList());

    // Si necesitas ver el JSON de nuevo en forma de cadena
    String jsonString = mapper.writeValueAsString(preguntas);
    System.out.println("JSON Serializado: " + jsonString);

    return preguntas;
}
}
