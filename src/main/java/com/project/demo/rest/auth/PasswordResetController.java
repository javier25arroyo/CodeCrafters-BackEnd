package com.project.demo.rest.auth;

import com.project.demo.logic.entity.auth.PasswordResetRequest;
import com.project.demo.service.MessageResponse;
import com.project.demo.service.PasswordResetService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetRequest request) {
        passwordResetService.requestPasswordReset(request.getEmail());

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body("If the email address exists, a password reset link will be sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("newPassword") String newPassword) {

        boolean success = passwordResetService.resetPassword(email, newPassword);

        if (!success) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Usuario no encontrado."));
        }

        return ResponseEntity.ok(new MessageResponse("Contraseña actualizada con éxito."));
    }
}
