package com.project.demo.rest.settings;

import com.project.demo.logic.entity.settings.Level;
import com.project.demo.logic.entity.settings.repository.LevelRepository;
import com.project.demo.logic.entity.game.Game;
import com.project.demo.logic.entity.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/levels")
public class LevelController {

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Level> getLevelById(@PathVariable Long id) {
        Optional<Level> level = levelRepository.findById(id);
        return level.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Level> createLevel(@RequestBody Level level) {
        if (level.getGame() != null && level.getGame().getId() != null) {
            Optional<Game> game = gameRepository.findById(level.getGame().getId());
            if (game.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            // para validar que el juego no tenga ya un nivel asignado
            if (game.get().getLevel() != null) {
                return ResponseEntity.badRequest().build();
            }
            level.setGame(game.get());
        }
        Level savedLevel = levelRepository.save(level);
        return ResponseEntity.ok(savedLevel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Level> updateLevel(@PathVariable Long id, @RequestBody Level levelDetails) {
        Optional<Level> optionalLevel = levelRepository.findById(id);
        if (optionalLevel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Level level = optionalLevel.get();
        level.setName(levelDetails.getName());
        level.setDescription(levelDetails.getDescription());
        if (levelDetails.getGame() != null && levelDetails.getGame().getId() != null) {
            Optional<Game> game = gameRepository.findById(levelDetails.getGame().getId());
            if (game.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            level.setGame(game.get());
        }
        Level updatedLevel = levelRepository.save(level);
        return ResponseEntity.ok(updatedLevel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLevel(@PathVariable Long id) {
        if (!levelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        levelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Level> deactivateLevel(@PathVariable Long id) {
        return levelRepository.findById(id).map(level -> {
            level.setActive(false);
            levelRepository.save(level);
            return ResponseEntity.ok(level);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Level> activateLevel(@PathVariable Long id) {
        return levelRepository.findById(id).map(level -> {
            level.setActive(true);
            levelRepository.save(level);
            return ResponseEntity.ok(level);
        }).orElse(ResponseEntity.notFound().build());
    }
}