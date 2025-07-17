package com.project.demo.logic.entity.game.repository;

import com.project.demo.logic.entity.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repositorio para la entidad {@link com.project.demo.logic.entity.game.Game}.
 * Proporciona métodos para realizar operaciones CRUD sobre los juegos.
 * (Actualmente sin implementar métodos específicos)
 */

public interface GameRepository extends JpaRepository<Game,Integer> {
        }
