package com.project.demo.service;

import com.project.demo.logic.entity.caregiver.Caregiver;
import com.project.demo.logic.entity.caregiver.UserCaregiver;
import com.project.demo.logic.entity.caregiver.repository.CaregiverRepository;
import com.project.demo.logic.entity.caregiver.repository.UserCaregiverRepository;
import com.project.demo.logic.entity.user.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CaregiverServiceImpl implements CaregiverService {

    @Autowired
    private CaregiverRepository caregiverRepo;
    @Autowired
    private UserCaregiverRepository userCaregiverRepo;

    @Override public Page<Caregiver> listar(Pageable p) {
        return caregiverRepo.findAll(p);
    }
    @Override public Caregiver crear(Caregiver c) {
        return caregiverRepo.save(c);
    }
    @Override public Optional<Caregiver> buscarPorId(Integer id) {
        return caregiverRepo.findById(id);
    }
    @Override public Optional<Caregiver> actualizar(Integer id, Caregiver c) {
        return caregiverRepo.findById(id)
                .map(existing -> {
                    existing.setName(c.getName());
                    existing.setPhone(c.getPhone());
                    return caregiverRepo.save(existing);
                });
    }
    @Override public boolean eliminar(Integer id) {
        if (caregiverRepo.existsById(id)) {
            caregiverRepo.deleteById(id);
            return true;
        }
        return false;
    }
    @Override public List<UserCaregiver> dashboardByEmail(String email) {
        return userCaregiverRepo.findAllByUserEmail(email);
    }

    @Override
     public List<UserSummary> getUsersForCaregiver(Integer caregiverId, String filter) {
                        List<UserCaregiver> rels = userCaregiverRepo.findByCaregiver_Id(caregiverId);
                        return rels.stream()
                            .map(rel -> {
                            var u = rel.getUser();
                            return new UserSummary(
                                        u.getName(),
                                        u.getEmail(),
                                        u.getEnabled(),
                                        u.getRole().getName().name()
                                           );
                        })
                          .filter(us -> filter == null
                               || us.getName().contains(filter)
                               || us.getEmail().contains(filter))
                           .collect(Collectors.toList());
    }

}
