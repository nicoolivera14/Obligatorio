package com.tuorganizacion.backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.backend.model.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class QuestionService {
  @Value("${openai.api-key}")
  private String apiKey;

  private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

  private final RestTemplate restTemplate;

  public QuestionService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<Question> fetchQuestionsForCategory(int count, String category) {
    return fetchQuestionsFromOpenAI(count, category);
  }

  public List<Question> fetchQuestionsFromOpenAI(int count, String category) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(apiKey);

    Map<String, Object> requestBody = getStringObjectMap(count, category);

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
    ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
      OPENAI_URL,
      HttpMethod.POST,
      request,
      new ParameterizedTypeReference<Map<String, Object>>() {}
    );

    return parseOpenAIResponse(Objects.requireNonNull(response.getBody()), category);
  }

  private static Map<String, Object> getStringObjectMap(int count, String category) {
    String prompt = "You are a trivia question generator. Generate " + count +
      " unique multiple-choice trivia questions in the category of \"" + category +
      "\" in JSON format, each with 4 options and the index of the correct answer. " +
      "Use this format: {\"questions\": [{\"text\": \"What is the capital of France?\", " +
      "\"options\": [\"Paris\", \"London\", \"Berlin\", \"Madrid\"], \"correctAnswer\": 0}]}";

    return Map.of(
      "model", "gpt-3.5-turbo",
      "messages", List.of(Map.of(
        "role", "system",
        "content", prompt
      )),
      "temperature", 0.7
    );
  }

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

    Map<String, Object> parsedResponse = parseJsonString(rawText);
    if (!parsedResponse.containsKey("questions")) {
      throw new RuntimeException("Invalid OpenAI response: missing 'questions'");
    }

    List<Map<String, Object>> questionData = (List<Map<String, Object>>) parsedResponse.get("questions");

    for (Map<String, Object> questionMap : questionData) {
      Question question = new Question();
      question.setText((String) questionMap.get("text"));
      question.setOptions((List<String>) questionMap.get("options"));
      question.setCorrectAnswer((Integer) questionMap.get("correctAnswer"));
      question.setCategory(category);
      question.setAnswered(false);

      questions.add(question);
    }

    return questions;
  }

  private Map<String, Object> parseJsonString(String jsonString) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(jsonString, Map.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse OpenAI response: " + e.getMessage(), e);
    }
  }
}

