package com.project.demo.rest.caregiver;

import com.project.demo.logic.entity.caregiver.Caregiver;
import com.project.demo.logic.entity.caregiver.UserCaregiver;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.user.UserSummary;
import com.project.demo.service.CaregiverService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los cuidadores.
 * Proporcionará endpoints para operaciones relacionadas con la gestión de cuidadores.
 *
 */
@RestController
@RequestMapping("/caregivers")
public class CaregiverController {
    @Autowired private CaregiverService service;

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public List<Caregiver> getAll(Pageable page) {
        return service.listar(page).getContent();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{caregiverId}/users")
    @PreAuthorize("hasRole('CAREGIVER')")
    public List<UserSummary> getUsers(
            @PathVariable Integer caregiverId,
            @RequestParam(required=false) String filter
    ) {
        return service.getUsersForCaregiver(caregiverId, filter);
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','CAREGIVER')")
    public ResponseEntity<?> dashboard(@RequestParam String email) {
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("email requerido");
        }
        return ResponseEntity.ok(service.dashboardByEmail(email));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','CAREGIVER')")
    public ResponseEntity<?> searchByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email)
                .map(u -> new UserSummary(
                        u.getName(),
                        u.getEmail(),
                        Boolean.TRUE.equals(u.getEnabled()),
                        u.getRole().getName().name()
                ))
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe un usuario con ese email"));
    }
}


