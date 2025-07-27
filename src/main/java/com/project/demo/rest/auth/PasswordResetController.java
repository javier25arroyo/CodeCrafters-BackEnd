package com.project.demo.rest.auth;

import com.project.demo.logic.entity.auth.PasswordResetRequest;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.service.EmailService;
import jakarta.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar las operaciones de restablecimiento de contraseña. Proporciona
 * endpoints para solicitar un restablecimiento de contraseña y para establecer una nueva
 * contraseña.
 */
@RestController
@RequestMapping("/auth")
public class PasswordResetController {

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private EmailService emailService;

    /** Patrón de expresión regular para validar el formato de un correo electrónico. */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /** Clase interna para encapsular mensajes de respuesta. */
    public static class MessageResponse {
        private String message;

        /**
         * Constructor para crear una nueva instancia de MessageResponse.
         *
         * @param message El mensaje a encapsular.
         */
        public MessageResponse(String message) {
            this.message = message;
        }

        /**
         * Obtiene el mensaje de la respuesta.
         *
         * @return El mensaje de texto.
         */
        public String getMessage() {
            return message;
        }

        /**
         * Establece el mensaje de la respuesta.
         *
         * @param message El mensaje de texto a establecer.
         */
        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * Endpoint para solicitar un restablecimiento de contraseña. Envía un correo electrónico con un
     * enlace de restablecimiento si el email existe en el sistema.
     *
     * @param request Objeto {@link PasswordResetRequest} que contiene el email del usuario.
     * @return ResponseEntity con un mensaje indicando el resultado de la operación.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetRequest request) {
        String email = request.getEmail();

        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is required."));
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid email format."));
        }

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            String resetToken = UUID.randomUUID().toString();

            try {
                emailService.sendPasswordResetEmail(email, resetToken);
                System.out.println("Email successfully sent to: " + email);
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError()
                        .body(new MessageResponse("Error sending email."));
            }
        }

        // For security, do not reveal if the email exists or not
        return ResponseEntity.ok()
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
            @RequestParam("email") String email, @RequestParam("newPassword") String newPassword) {

        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is required."));
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid email format."));
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("The new password is mandatory."));
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found."));
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Password updated successfully."));
    }
}
