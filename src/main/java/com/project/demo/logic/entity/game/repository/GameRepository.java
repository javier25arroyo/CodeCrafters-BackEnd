package com.project.demo.logic.entity.game.repository;

import com.project.demo.logic.entity.game.Game;
import com.project.demo.logic.entity.game.GameTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad {@link com.project.demo.logic.entity.game.Game}.
 * Proporciona métodos para realizar operaciones CRUD sobre los juegos.
 */
public interface GameRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findFirstByGameType(GameTypeEnum gameType);
}
