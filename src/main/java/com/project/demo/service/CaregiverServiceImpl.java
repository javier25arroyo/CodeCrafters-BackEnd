package com.project.demo.service;

import com.project.demo.logic.entity.caregiver.Caregiver;
import com.project.demo.logic.entity.caregiver.UserCaregiver;
import com.project.demo.logic.entity.caregiver.repository.CaregiverRepository;
import com.project.demo.logic.entity.caregiver.repository.UserCaregiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaregiverServiceImpl implements CaregiverService {
    @Autowired
    private CaregiverRepository caregiverRepo;
    @Autowired private UserCaregiverRepository userCaregiverRepo;

    @Override
    public Page<Caregiver> listar(Pageable p) {
        return null;
    }

    @Override
    public Optional<Caregiver> buscarPorId(Integer id) {
        return Optional.empty();
    }

    @Override
    public Caregiver crear(Caregiver c) {
        return null;
    }

    @Override
    public Optional<Caregiver> actualizar(Integer id, Caregiver c) {
        return Optional.empty();
    }

    @Override
    public boolean eliminar(Integer id) {
        return false;
    }

    @Override
    public List<UserCaregiver> dashboardByEmail(String email) {
        return userCaregiverRepo.findByUserEmail(email);
    }
}