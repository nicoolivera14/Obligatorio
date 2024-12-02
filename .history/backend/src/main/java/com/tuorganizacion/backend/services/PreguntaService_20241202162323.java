package com.tuorganizacion.backend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
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
                "textoPregunta": "Texto de la pregunta",
                "categoria": "categoria",
                "opciones": ["textoOpcion 1", "textoOpcion 2", "textoOpcion 3", "textoOpcion 4"],
                "respuestaCorrecta": Índice de la respuesta correcta (0-3)
            }
        ]
        """, cantPreguntas, Constantes.CATEGORIAS.toString());

        String preguntasJson = openAIService.consultaGPT(prompt);
        private List<Question> parseOpenAIResponse(Map<String, Object> response, String category) {
    List<Question> questions = new ArrayList<>();

    if (response == null && !response.containsKey("choices")) {
      throw new RuntimeException("Invalid response from OpenAI: missing 'choices'");
    }

    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
    if (choices == null || choices.isEmpty()) {
      throw new RuntimeException("Invalid OpenAI response: 'choices' is empty");
    }

    Map<String, Object> firstChoice = choices.get(0);
    String rawText = (String) ((Map<String, Object>) firstChoice.get("message")).get("content");
    
        Map<String, Object> parsedResponse = parseJsonString(preguntasJson);
        if (!parsedResponse.containsKey("questions")) {
            throw new RuntimeException("Invalid OpenAI response: missing 'questions'");
        }

        List<Map<String, Object>> questionData = (List<Map<String, Object>>) parsedResponse.get("preguntas");
         
        for (Map<String, Object> questionMap : questionData) {
            Pregunta pregunta = new Pregunta();
            pregunta.setTextoPregunta((String) questionMap.get("text"));
            pregunta.setOpciones((List<String>) questionMap.get("options"));
            pregunta.setRespuestaCorrecta((Integer) questionMap.get("correctAnswer"));
            pregunta.setCategoria(questionMap.get("categoria"));
            pregunta.setRespondida(false);

      preguntas.add(pregunta);
    }

    return preguntas;
  }

  private Map<String, Object> parseJsonString(String jsonString) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(jsonString, Map.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse OpenAI response: " + e.getMessage(), e);
    }
  }
        // Convertir el JSON en un objeto Pregunta
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(preguntasJson,
         new TypeReference<List<Pregunta>>() {}
         );
         
    }
}

