package com.tuorganizacion.backend.model;

import java.util.List;

public class GPTRequest {
    private String model;
    private List<Message> messages;

    // Getters and Setters
    public List<Message> getMessages() {
        return messages;
    }
    public String getModel() {
        return model;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    public void setModel(String model) {
        this.model = model;
    }

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