package com.project.demo.rest.game;

import com.project.demo.logic.entity.game.Score;
import com.project.demo.logic.entity.game.GameTypeEnum;
import com.project.demo.logic.entity.game.repository.GameRepository;
import com.project.demo.logic.entity.game.repository.ScoreRepository;
import com.project.demo.logic.service.PuzzleScoreCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Optional;

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
     * Calculadora de puntuaciones para puzzles.
     */
    @Autowired
    private PuzzleScoreCalculator puzzleScoreCalculator;

    /**
     * Guarda la puntuación de un juego para el usuario autenticado.
     * El usuario se obtiene del contexto de seguridad.
     * La fecha se establece en el momento de la creación.
     * Para puzzles, calcula automáticamente la puntuación basada en tiempo y movimientos.
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
        var game = gameRepository.findFirstByGameType(score.getGameType())
                .orElseThrow(() -> new RuntimeException("Game not found for type: " + score.getGameType()));
        score.setGame(game);

        // Si es un puzzle, calcular la puntuación automáticamente
        if (score.getGameType() == GameTypeEnum.PUZZLE) {
            double calculatedScore = puzzleScoreCalculator.calculateScore(
                score.getLevel(), 
                score.getTime(), 
                score.getMovements()
            );
            score.setScore(calculatedScore);
        } else if (score.getGameType() == GameTypeEnum.MUSIC_MEMORY) {
        // Aquí asumimos que score ya viene calculado en frontend,
        // por lo que no hay que recalcular nada.
        // Solo aseguramos que movements y time sean cero o nulos
        score.setMovements(0);
        score.setTime(0L);

    }

        Score savedScore = scoreRepository.save(score);
        return ResponseEntity.ok(savedScore);
    }

    @PostMapping("/score/music-melody")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Score> saveMusicMelodyScore(@RequestBody Score score) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userRepository.findByEmail(currentUserName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        score.setUser(user);
        score.setDate(new Date());

        var game = gameRepository.findFirstByGameType(GameTypeEnum.MUSIC_MEMORY)
                .orElseThrow(() -> new RuntimeException("Game not found for type MUSIC_MEMORY"));
        score.setGame(game);
        score.setGameType(GameTypeEnum.MUSIC_MEMORY);

        Optional<Score> existingScoreOpt = scoreRepository.findByUserIdAndGameTypeAndLevel(
                user.getId(),
                GameTypeEnum.MUSIC_MEMORY,
                score.getLevel()
        );

        if (existingScoreOpt.isPresent()) {
            Score existingScore = existingScoreOpt.get();
            existingScore.setScore(existingScore.getScore() + score.getScore());
            existingScore.setDate(new Date());
            Score updated = scoreRepository.save(existingScore);
            return ResponseEntity.ok(updated);
        } else {

            Score savedScore = scoreRepository.save(score);
            return ResponseEntity.ok(savedScore);
        }
    }

    /**
     * Constructor por defecto.
     */
    public GameController() {
    }
}
