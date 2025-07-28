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

    /**
     * Crea una nueva sugerencia.
     *
     * @param request El objeto {@link SuggestionRequest} que contiene los datos de la sugerencia.
     * @return ResponseEntity con un mensaje de éxito o error.
     */
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
     * Obtiene todas las sugerencias.
     *
     * @return ResponseEntity con una lista de {@link Suggestion}.
     */
    @GetMapping
    public ResponseEntity<List<Suggestion>> getAllSuggestions() {
        List<Suggestion> suggestions = suggestionRepository.findAll();
        return ResponseEntity.ok(suggestions);
    }

    /**
     * Actualiza el estado de una sugerencia.
     *
     * @param id   El ID de la sugerencia a actualizar.
     * @param body El cuerpo de la solicitud que contiene el nuevo estado.
     * @return ResponseEntity con un mensaje de éxito o error.
     */
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

    /**
     * Record que representa la estructura de una solicitud para crear una sugerencia.
     *
     * @param userId El ID del usuario que envía la sugerencia.
     * @param message El contenido del mensaje de la sugerencia.
     */
    public record SuggestionRequest(Integer userId, String message) {
    }
}
