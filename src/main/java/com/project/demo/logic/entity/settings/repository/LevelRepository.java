package com.project.demo.logic.entity.settings.repository;

import com.project.demo.logic.entity.settings.Level;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad {@link Level}.
 * Proporciona métodos para realizar operaciones CRUD sobre los niveles.
 */
public interface LevelRepository extends JpaRepository<Level,Long>{
        }