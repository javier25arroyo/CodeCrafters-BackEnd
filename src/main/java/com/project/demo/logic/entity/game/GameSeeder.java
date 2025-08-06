package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.game.repository.GameRepository;
import com.project.demo.logic.entity.settings.LevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeder para inicializar los juegos en la base de datos.
 * Se ejecuta al iniciar la aplicación y crea los registros necesarios
 * para todos los tipos de juego disponibles.
 */
@Component
public class GameSeeder implements CommandLineRunner {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public void run(String... args) throws Exception {
        if (gameRepository.count() == 0) {
            seedGames();
        }
    }

    /**
     * Crea los registros iniciales de juegos para todos los tipos disponibles.
     */
    private void seedGames() {
        for (GameTypeEnum gameType : GameTypeEnum.values()) {
            if (!gameRepository.findFirstByGameType(gameType).isPresent()) {
                Game game = new Game();
                game.setGameType(gameType);
                game.setLevel(null);
                gameRepository.save(game);
            }
        }
    }
}