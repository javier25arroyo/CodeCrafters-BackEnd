package com.project.demo.service;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.exceptions.BusinessException;
import com.project.demo.logic.exceptions.ValidationException;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final LoggingService loggingService;
    private final PasswordService passwordService;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );

    public PasswordResetService(UserRepository userRepository, EmailService emailService,
                                LoggingService loggingService, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.loggingService = loggingService;
        this.passwordService = passwordService;
    }

    public void requestPasswordReset(String email) {
        if (email == null || email.trim().isEmpty()) {
            loggingService.logSecurityEvent("FORGOT_PASSWORD_INVALID_EMAIL", "Empty email provided", null);
            throw new ValidationException.RequiredFieldException("email", "forgotPassword");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            loggingService.logSecurityEvent("FORGOT_PASSWORD_INVALID_FORMAT", "Invalid email format: " + email, null);
            throw new ValidationException.InvalidEmailException(email, "forgotPassword");
        }

        userRepository.findByEmail(email).ifPresent(user -> {
            String resetToken = UUID.randomUUID().toString();
            try {
                emailService.sendPasswordResetEmail(email, resetToken);
                loggingService.logSecurityEvent("PASSWORD_RESET_EMAIL_SENT", "Reset email sent successfully", email);
            } catch (MessagingException e) {
                loggingService.logError(e, "PasswordResetService", "requestPasswordReset", "SEND_RESET_EMAIL");
                throw new BusinessException.EmailServiceException("forgotPassword", e);
            }
        });
    }

    public boolean resetPassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        user.setPassword(passwordService.encode(newPassword));
        userRepository.save(user);
        return true;
    }

}
