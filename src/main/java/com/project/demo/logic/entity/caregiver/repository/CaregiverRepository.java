package com.project.demo.logic.entity.caregiver.repository;

import com.project.demo.logic.entity.caregiver.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad {@link com.project.demo.logic.entity.caregiver.Caregiver}.
 * Proporciona métodos para realizar operaciones CRUD sobre los cuidadores.
 * (Actualmente sin implementar métodos específicos)
 */
public interface CaregiverRepository extends JpaRepository<Caregiver,Integer> {
}