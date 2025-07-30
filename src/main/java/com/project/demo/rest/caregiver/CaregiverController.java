package com.project.demo.rest.caregiver;

import com.project.demo.logic.entity.caregiver.UserCaregiver;
import com.project.demo.service.CaregiverService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para gestionar los cuidadores.
 * Proporcionará endpoints para operaciones relacionadas con la gestión de cuidadores.
 *
 */
@RestController
@RequestMapping("/caregivers")
public class CaregiverController {
    @Autowired
    private CaregiverService service;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('CAREGIVER')")
    public ResponseEntity<List<UserCaregiver>> dashboardByEmail(
            @RequestParam("email") String email,
            HttpServletRequest request) {

        List<UserCaregiver> datos = service.dashboardByEmail(email);
        return ResponseEntity.ok(datos);
    }
}

