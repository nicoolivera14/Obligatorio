package com.tuorganizacion.backend.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SaveCategoryController {

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryRequest categoryRequest, HttpSession session) {
        try {
            // Guarda la categoría ganadora en la sesión
            session.setAttribute("winningCategory", categoryRequest.getWinningCategory());
            return ResponseEntity.ok("Category saved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
    }

    // Clase DTO para mapear la solicitud JSON
    public static class CategoryRequest {
        private String winningCategory;

        public String getWinningCategory() {
            return winningCategory;
        }

        public void setWinningCategory(String winningCategory) {
            this.winningCategory = winningCategory;
        }
    }
}