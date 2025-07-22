package com.project.demo.rest.notification;

import com.project.demo.logic.entity.notification.Suggestion;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.notification.repository.SuggestionRepository;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Controlador REST para gestionar sugerencias.
 */
@RestController
@RequestMapping("/api/suggestions")
@CrossOrigin(origins = "*") // Ajustalo al frontend si lo necesitás
public class SuggestionController {

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createSuggestion(@RequestBody SuggestionRequest request) {
        User user = userRepository.findById(request.getUserId().longValue()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado"));
        }

        Suggestion suggestion = new Suggestion();
        suggestion.setUser(user);
        suggestion.setMessage(request.getMessage());
        suggestion.setStatus("pendiente"); // Estado inicial
        suggestion.setDate(LocalDateTime.now());

        suggestionRepository.save(suggestion);

        return ResponseEntity.ok(Map.of("message", "Sugerencia recibida"));
    }

    // DTO interno para recibir los datos desde el frontend
    public static class SuggestionRequest {
        private Integer userId;
        private String message;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
