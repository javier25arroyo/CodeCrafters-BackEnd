package com.project.demo.logic.entity.notification.repository;

import com.project.demo.logic.entity.notification.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link Suggestion}.
 * Permite realizar operaciones CRUD sobre la tabla 'suggestions'.
 */
@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
}
