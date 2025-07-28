package com.project.demo.logic.entity.notification.repository;

import com.project.demo.logic.entity.notification.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad {@link Suggestion}.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas sobre sugerencias.
 */
@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
    /**
     * Busca sugerencias por el ID de usuario.
     *
     * @param userId El ID del usuario.
     * @return Una lista de sugerencias asociadas al usuario.
     */
    List<Suggestion> findByUserId(Long userId); // opcional, útil si necesitás filtrar por usuario
}