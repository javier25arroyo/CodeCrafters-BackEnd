package com.project.demo.rest.auth;

import com.project.demo.logic.entity.auth.PasswordResetRequest;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.exceptions.BusinessException;
import com.project.demo.logic.exceptions.ValidationException;
import com.project.demo.service.EmailService;
import com.project.demo.service.LoggingService;
import com.project.demo.service.MessageResponse;
import com.project.demo.service.PasswordService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Controlador REST para gestionar las operaciones de restablecimiento de contraseña.
 * Proporciona endpoints para solicitar un restablecimiento de contraseña y para establecer una nueva contraseña.
 */
@RestController
@RequestMapping("/auth")
public class PasswordResetController {

    /**
     * Constructor por defecto.
     */
    public PasswordResetController() {
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private LoggingService loggingService;

    /**
     * Patrón de expresión regular para validar el formato de un correo electrónico.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );


    /**
     * Endpoint para solicitar un restablecimiento de contraseña.
     * Envía un correo electrónico con un enlace de restablecimiento si el email existe en el sistema.
     *
     * @param request Objeto {@link PasswordResetRequest} que contiene el email del usuario.
     * @return ResponseEntity con un mensaje indicando el resultado de la operación.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetRequest request) {
        String email = request.getEmail();

        if (email == null || email.trim().isEmpty()) {
            loggingService.logSecurityEvent("FORGOT_PASSWORD_INVALID_EMAIL", "Empty email provided", null);
            throw new ValidationException.RequiredFieldException("email", "forgotPassword");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            loggingService.logSecurityEvent("FORGOT_PASSWORD_INVALID_FORMAT", "Invalid email format: " + email, null);
            throw new ValidationException.InvalidEmailException(email, "forgotPassword");
        }

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            String resetToken = UUID.randomUUID().toString();

            try {
                emailService.sendPasswordResetEmail(email, resetToken);
                loggingService.logSecurityEvent("PASSWORD_RESET_EMAIL_SENT", "Reset email sent successfully", email);
            } catch (MessagingException e) {
                loggingService.logError(e, "PasswordResetController", "forgotPassword", "SEND_RESET_EMAIL");
                throw new BusinessException.EmailServiceException("forgotPassword", e);
            }
        }

        // For security, do not reveal if the email exists or not
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body("If the email address exists, a password reset link will be sent.");
    }

    /**
     * Endpoint para establecer una nueva contraseña.
     *
     * @param email El correo electrónico del usuario.
     * @param newPassword La nueva contraseña a establecer.
     * @return ResponseEntity con un mensaje indicando el resultado de la operación.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("newPassword") String newPassword) {

        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Email is required."));
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Invalid email format."));
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("The new password is mandatory."));
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("User not found."));
        }

        User user = userOptional.get();
        user.setPassword(passwordService.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Password updated successfully."));
    }
}
