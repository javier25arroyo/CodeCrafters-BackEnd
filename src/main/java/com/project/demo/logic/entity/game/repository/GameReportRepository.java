package com.project.demo.logic.entity.game.repository;

import com.project.demo.logic.entity.game.GameReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link com.project.demo.logic.entity.game.GameReport}.
 * Proporciona métodos para realizar operaciones CRUD sobre los reportes de juegos.
 */
@Repository
public interface GameReportRepository extends JpaRepository<GameReport, Integer> {
}
