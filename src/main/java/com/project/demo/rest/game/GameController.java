package com.project.demo.rest.game;

import com.project.demo.logic.entity.game.Game;
import com.project.demo.logic.entity.settings.Level;
import com.project.demo.logic.entity.game.repository.GameRepository;
import com.project.demo.logic.entity.settings.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private LevelRepository levelRepository;

    @GetMapping
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Integer id) {
        Optional<Game> game = gameRepository.findById(id);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game savedGame = gameRepository.save(game);
        return ResponseEntity.ok(savedGame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Integer id, @RequestBody Game gameDetails) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Game game = optionalGame.get();
        game.setName(gameDetails.getName());
        game.setDescription(gameDetails.getDescription());
        game.setImageUrl(gameDetails.getImageUrl());
        game.setStatus(gameDetails.getStatus());
        game.setCategory(gameDetails.getCategory());
        game.setComponents(gameDetails.getComponents());
        game.setSessions(gameDetails.getSessions());
        game.setReports(gameDetails.getReports());
        game.setFavoritedBy(gameDetails.getFavoritedBy());
        game.setStreaks(gameDetails.getStreaks());
        Game updatedGame = gameRepository.save(game);
        return ResponseEntity.ok(updatedGame);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Integer id) {
        if (!gameRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gameRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/level/{levelId}")
    public ResponseEntity<Game> assignLevel(@PathVariable Integer id, @PathVariable Long levelId) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        Optional<Level> optionalLevel = levelRepository.findById(levelId);

        if (optionalGame.isEmpty() || optionalLevel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Game game = optionalGame.get();
        Level level = optionalLevel.get();

        // Validar que el nivel no esté asociado a otro juego
        if (level.getGame() != null) {
            return ResponseEntity.badRequest().build();
        }

        // Validar que el juego no tenga ya un nivel asignado
        if (game.getLevel() != null) {
            return ResponseEntity.badRequest().build();
        }

        game.setLevel(level);
        level.setGame(game);
        gameRepository.save(game);
        levelRepository.save(level);

        return ResponseEntity.ok(game);
    }

    @PostMapping("/{id}/levels")
    public ResponseEntity<Level> createLevelForGame(@PathVariable Integer id, @RequestBody Level level) {
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Game game = optionalGame.get();

        // Validar que el juego no tenga ya un nivel asignado
        if (game.getLevel() != null) {
            return ResponseEntity.badRequest().build();
        }

        level.setGame(game);
        game.setLevel(level);
        Level savedLevel = levelRepository.save(level);
        gameRepository.save(game);

        return ResponseEntity.ok(savedLevel);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Game> deactivateGame(@PathVariable Integer id) {
        return gameRepository.findById(id).map(game -> {
            game.setActive(false);
            gameRepository.save(game);
            return ResponseEntity.ok(game);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Game> activateGame(@PathVariable Integer id) {
        return gameRepository.findById(id).map(game -> {
            game.setActive(true);
            gameRepository.save(game);
            return ResponseEntity.ok(game);
        }).orElse(ResponseEntity.notFound().build());
    }
}