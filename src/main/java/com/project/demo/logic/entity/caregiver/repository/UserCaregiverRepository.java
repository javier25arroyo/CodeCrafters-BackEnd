package com.project.demo.logic.entity.caregiver.repository;

import com.project.demo.logic.entity.caregiver.UserCaregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio para la entidad {@link com.project.demo.logic.entity.caregiver.UserCaregiver}.
 * Proporciona métodos para realizar operaciones CRUD sobre las relaciones usuario-cuidador.
 * (Actualmente sin implementar métodos específicos)
 */
public interface UserCaregiverRepository extends JpaRepository<UserCaregiver,Integer> {
    // Buscar todas las relaciones para un email de usuario
    @Query("select uc from UserCaregiver uc where uc.user.email = :email")
    List<UserCaregiver> findAllByUserEmail(@Param("email") String email);

    // Buscar todas las relaciones para un cuidador por su ID
    List<UserCaregiver> findByCaregiver_Id(Integer caregiverId);
}