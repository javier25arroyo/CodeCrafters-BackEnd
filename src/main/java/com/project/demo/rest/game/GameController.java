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
        game.setFeedbacks(gameDetails.getFeedbacks());
        game.setSessions(gameDetails.getSessions());
        game.setReports(gameDetails.getReports());
        game.setFavoritedBy(gameDetails.getFavoritedBy());
        game.setStreaks(gameDetails.getStreaks());
        game.setLevels(gameDetails.getLevels());
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

    @GetMapping("/{id}/levels")
    public ResponseEntity<List<Level>> getLevelsByGame(@PathVariable Integer id) {
        Optional<Game> game = gameRepository.findById(id);
        return game.map(g -> ResponseEntity.ok(g.getLevels()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/levels")
    public ResponseEntity<Level> addLevelToGame(@PathVariable Integer id, @RequestBody Level level) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        level.setGame(game.get());
        Level savedLevel = levelRepository.save(level);
        return ResponseEntity.ok(savedLevel);
}
}