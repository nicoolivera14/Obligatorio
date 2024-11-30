package com.tuorganizacion.backend.model;

import java.util.List;

public class GPTRequest {
    private String model;
    private List<Message> messages;
    private Double temperature;

    // Getters and Setters

    public static class Message {
        private String role;
        private String content;

        // Constructor
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        // Getters and Setters
        public void setRole(String role) {
            this.role = role;
        }
        public void setContent(String content) {
            this.content = content;
        }
        
        public String getContent() {
            return content;
        }
        public String getRole() {
            return role;
        }
    }
}