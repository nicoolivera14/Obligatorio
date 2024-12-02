package com.tuorganizacion.backend.websockets;

import com.tuorganizacion.backend.services.SalaJuegoService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
public class WebSocketEventListener {
  private final SalaJuegoService salaJuegoService;

  public WebSocketEventListener(SalaJuegoService salaJuegoService) {
    this.salaJuegoService = salaJuegoService;
  }

  @EventListener
  public void handleSessionDisconnect(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
    String sessionKey = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("salaKey");

    if (username != null && salaKey != null) {
      salaJuegoService.handlePlayerDisconnection(salaKey, username);
    }
  }
}
