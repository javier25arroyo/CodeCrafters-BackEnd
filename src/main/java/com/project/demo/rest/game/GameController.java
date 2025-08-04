package com.project.demo.rest.game;

import com.project.demo.logic.entity.game.GameReport;
import com.project.demo.logic.entity.game.games.PuzzleResultDTO;
import com.project.demo.logic.entity.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.service.GameService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Controlador REST para gestionar los juegos.
 * Proporciona endpoints para operaciones relacionadas con la gestión de juegos.
 */
@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/puzzle/save")
    public ResponseEntity<?> savePuzzleResult(@RequestBody PuzzleResultDTO puzzleResult) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            User user = userRepository.findByEmail(currentUserName)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            // Llamar al servicio y envolver el resultado en un ResponseEntity
            GameReport gameReport = gameService.savePuzzleResult(user, puzzleResult);
            return ResponseEntity.ok(gameReport);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }

    /**
     * Constructor por defecto.
     */
    public GameController() {
    }
}
