package com.project.demo.rest.game;

import com.project.demo.logic.entity.game.Score;
import com.project.demo.logic.entity.game.repository.GameRepository;
import com.project.demo.logic.entity.game.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * Controlador para gestionar las operaciones relacionadas con los juegos.
 */
@RestController
@RequestMapping("/games")
public class GameController {

    /**
     * Repositorio para acceder a los datos de los juegos.
     */
    @Autowired
    private GameRepository gameRepository;

    /**
     * Repositorio para acceder a los datos de las puntuaciones.
     */
    @Autowired
    private ScoreRepository scoreRepository;

    /**
     * Repositorio para acceder a los datos de los usuarios.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Guarda la puntuación de un juego para el usuario autenticado.
     * El usuario se obtiene del contexto de seguridad.
     * La fecha se establece en el momento de la creación.
     *
     * @param score El objeto Score con los datos de la puntuación a guardar.
     * @return ResponseEntity con la puntuación guardada.
     */
    @PostMapping("/score")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Score> saveScore(@RequestBody Score score) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userRepository.findByEmail(currentUserName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        score.setUser(user);
        score.setDate(new Date());
        Score savedScore = scoreRepository.save(score);
        return ResponseEntity.ok(savedScore);
    }

    /**
     * Constructor por defecto.
     */
    public GameController() {
    }
}
