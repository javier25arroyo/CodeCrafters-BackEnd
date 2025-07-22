package com.project.demo.rest.notification;

import com.project.demo.logic.entity.notification.Suggestion;
import com.project.demo.logic.entity.notification.repository.SuggestionRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Controlador REST para gestionar sugerencias.
 */
@RestController
@RequestMapping("/api/suggestions")
@CrossOrigin(origins = "*")
public class SuggestionController {

    private final SuggestionRepository suggestionRepository;
    private final UserRepository userRepository;

    public SuggestionController(SuggestionRepository suggestionRepository, UserRepository userRepository) {
        this.suggestionRepository = suggestionRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createSuggestion(@RequestBody SuggestionRequest request) {
        User user = userRepository.findById(request.userId().longValue()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado"));
        }

        Suggestion suggestion = new Suggestion();
        suggestion.setUser(user);
        suggestion.setMessage(request.message());
        suggestion.setStatus("pendiente");
        suggestion.setDate(LocalDateTime.now());

        suggestionRepository.save(suggestion);

        return ResponseEntity.ok(Map.of("message", "Sugerencia recibida"));
    }

    /**
         * DTO para recibir los datos del frontend.
         */
        public record SuggestionRequest(Integer userId, String message) {
    }
}
