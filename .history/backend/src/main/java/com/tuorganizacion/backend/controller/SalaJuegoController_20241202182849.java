package com.trivia.controller;

import com.trivia.dto.GameSessionDTO;
import com.trivia.dto.PlayerResponseDTO;
import com.trivia.model.GameConstants;
import com.trivia.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameSessionController {
  private final GameSessionService gameSessionService;

  public GameSessionController(GameSessionService gameSessionService) {
    this.gameSessionService = gameSessionService;
  }

  @GetMapping("/categories")
  public ResponseEntity<List<String>> getCategories() {
    try {
      return ResponseEntity.ok(GameConstants.CATEGORIES);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/single-player/start")
  public ResponseEntity<GameSessionDTO> startSinglePlayerSession() {
    try {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      GameSessionDTO gameSessionDTO = gameSessionService.startSinglePlayerSession(username);

      return ResponseEntity.ok(gameSessionDTO);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/multi-player/start")
  public ResponseEntity<GameSessionDTO> startMultiPlayerSession() {
    try {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      GameSessionDTO gameSessionDTO = gameSessionService.startMultiPlayerSession(username);

      return ResponseEntity.ok(gameSessionDTO);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/multi-player/join")
  public ResponseEntity<Void> joinMultiplayerSession(@RequestParam String sessionKey) {
    try {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      gameSessionService.joinMultiPlayerSession(sessionKey, username);

      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{sessionKey}/spin-roulette")
  public ResponseEntity<Void> getNextQuestion(@PathVariable String sessionKey) {
    try {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      gameSessionService.spinRouletteAndFetchQuestions(sessionKey, username);

      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/{sessionKey}/submit-answer")
  public ResponseEntity<Map<String, Object>> submitAnswer(
    @PathVariable String sessionKey, @RequestBody PlayerResponseDTO request
  ) {
    try {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      Map<String, Object> response = gameSessionService.handleSubmitAnswer(sessionKey, username, request);

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/{sessionKey}/end")
  public ResponseEntity<Void> endSession(@PathVariable String sessionKey) {
    try {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      gameSessionService.handleEndGameSession(sessionKey, username);

      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
