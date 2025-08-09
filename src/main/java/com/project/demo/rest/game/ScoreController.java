package com.project.demo.rest.game;

import com.project.demo.logic.entity.game.Game;
import com.project.demo.logic.entity.game.GameTypeEnum;
import com.project.demo.logic.entity.game.Score;
import com.project.demo.logic.entity.game.repository.GameRepository;
import com.project.demo.logic.entity.game.repository.ScoreRepository;
import com.project.demo.logic.entity.settings.LevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar las puntuaciones de los juegos.
 */
@RestController
@RequestMapping("/scores")
public class ScoreController {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private GameRepository gameRepository;

    /**
     * Obtiene todas las puntuaciones.
     * @return Una lista de todas las puntuaciones.
     */
    @GetMapping
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    /**
     * Agrega una nueva puntuación.
     * @param score La puntuación a agregar.
     * @return La puntuación agregada.
     */
    @PostMapping
    public Score addScore(@RequestBody Score score) {
        return scoreRepository.save(score);
    }

    /**
     * Obtiene una puntuación por su ID.
     * @param id El ID de la puntuación.
     * @return La puntuación con el ID especificado, o null si no se encuentra.
     */
    @GetMapping("/{id}")
    public Score getScoreById(@PathVariable Long id) {
        return scoreRepository.findById(id).orElse(null);
    }

    /**
     * Actualiza una puntuación existente.
     * @param id El ID de la puntuación a actualizar.
     * @param score La nueva información de la puntuación.
     * @return La puntuación actualizada, o null si no se encuentra.
     */
    @PutMapping("/{id}")
    public Score updateScore(@PathVariable Long id, @RequestBody Score score) {
        return scoreRepository.findById(id)
                .map(existingScore -> {
                    existingScore.setUser(score.getUser());
                    existingScore.setGame(score.getGame());
                    existingScore.setGameType(score.getGameType());
                    existingScore.setLevel(score.getLevel());
                    existingScore.setMovements(score.getMovements());
                    existingScore.setTime(score.getTime());
                    existingScore.setScore(score.getScore());
                    existingScore.setDate(score.getDate());
                    return scoreRepository.save(existingScore);
                })
                .orElse(null);
    }

    /**
     * Elimina una puntuación por su ID.
     * @param id El ID de la puntuación a eliminar.
     */
    @DeleteMapping("/{id}")
    public void deleteScore(@PathVariable Long id) {
        scoreRepository.deleteById(id);
}

    @PutMapping("/{gameType}/level")
    public ResponseEntity<Game> updateLevel(
            @PathVariable GameTypeEnum gameType,
            @RequestParam LevelEnum level) {

        Game game = gameRepository.findFirstByGameType(gameType)
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        game.setLevel(level);
        gameRepository.save(game);

        return ResponseEntity.ok(game);
    }
}