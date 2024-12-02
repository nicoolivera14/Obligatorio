package com.tuorganizacion.backend.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
  private final SimpMessagingTemplate messagingTemplate;

  public WebSocketService(SimpMessagingTemplate simpMessagingTemplate) {
    this.messagingTemplate = simpMessagingTemplate;
  }

  public void broadcastUpdate(String sessionKey, Object message) {
    messagingTemplate.convertAndSend("/topic/game/" + sessionKey, message);
  }
}