package com.project.demo.service;

import com.project.demo.logic.entity.caregiver.Caregiver;
import com.project.demo.logic.entity.caregiver.UserCaregiver;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CaregiverService {
    Page<Caregiver> listar(Pageable p);
    Optional<Caregiver> buscarPorId(Integer id);
    Caregiver crear(Caregiver c);
    Optional<Caregiver> actualizar(Integer id, Caregiver c);
    boolean eliminar(Integer id);
    List<UserCaregiver> dashboardByEmail(String email);
}
