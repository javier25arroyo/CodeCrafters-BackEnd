package com.project.demo.logic.entity.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link UserSettings}.
 * Proporciona métodos para realizar operaciones CRUD sobre la configuración de usuario.
 */
@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Integer> {
}