package com.trivia.dto;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameSessionDTO {
  private String sessionKey;
  private List<PlayerDTO> players;
  private String status;
  private Instant createdAt;
}
