package com.project.demo.rest.notification;

import com.project.demo.logic.entity.notification.Suggestion;
import com.project.demo.logic.entity.notification.repository.SuggestionRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createSuggestion(@RequestBody Map<String, Object> payload) {
        Long userId = Long.parseLong(payload.get("userId").toString());
        String message = payload.get("message").toString();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Suggestion suggestion = new Suggestion();
        suggestion.setUser(user);
        suggestion.setMessage(message);
        suggestion.setStatus("pendiente");
        suggestion.setDate(LocalDateTime.now());

        suggestionRepository.save(suggestion);

        return ResponseEntity.ok(Map.of(
                "message", "¡Gracias por tu sugerencia!"
        ));
    }
}
