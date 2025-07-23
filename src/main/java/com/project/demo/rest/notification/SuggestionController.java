package com.project.demo.rest.notification;

import com.project.demo.logic.entity.notification.Suggestion;
import com.project.demo.logic.entity.notification.repository.SuggestionRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar sugerencias.
 */
@RestController
@RequestMapping("/suggestions")
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

    @GetMapping
    public ResponseEntity<List<Suggestion>> getAllSuggestions() {
        List<Suggestion> suggestions = suggestionRepository.findAll();
        return ResponseEntity.ok(suggestions);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateSuggestionStatus(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        Suggestion suggestion = suggestionRepository.findById(id).orElse(null);
        if (suggestion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Sugerencia no encontrada"));
        }

        String newStatus = body.get("status");
        if (newStatus == null || newStatus.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Estado inválido"));
        }

        suggestion.setStatus(newStatus);
        suggestionRepository.save(suggestion);

        return ResponseEntity.ok(Map.of("message", "Estado actualizado"));
    }

    public record SuggestionRequest(Integer userId, String message) {
    }
}
