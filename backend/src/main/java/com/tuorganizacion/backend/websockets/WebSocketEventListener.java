package com.tuorganizacion.backend.websockets;

import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.tuorganizacion.backend.services.GameSessionService;

@Component
public class WebSocketEventListener {
  private final GameSessionService gameSessionService;

  public WebSocketEventListener(GameSessionService gameSessionService) {
    this.gameSessionService = gameSessionService;
  }

  @EventListener
  public void handleSessionDisconnect(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
    String sessionKey = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("sessionKey");

    if (username != null && sessionKey != null) {
      gameSessionService.handlePlayerDisconnection(sessionKey, username);
    }
  }
}