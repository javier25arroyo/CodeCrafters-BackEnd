package com.project.demo.logic.entity.caregiver.repository;

import com.project.demo.logic.entity.caregiver.UserCaregiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para la entidad {@link com.project.demo.logic.entity.caregiver.UserCaregiver}.
 * Proporciona métodos para realizar operaciones CRUD sobre las relaciones usuario-cuidador.
 * (Actualmente sin implementar métodos específicos)
 */
public interface UserCaregiverRepository extends JpaRepository<UserCaregiver,Integer> {
    List<UserCaregiver> findByUserEmail(String email);

}
