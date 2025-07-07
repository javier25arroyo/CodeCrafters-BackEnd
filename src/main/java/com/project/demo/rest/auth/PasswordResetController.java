package com.project.demo.rest.auth;

import com.project.demo.logic.entity.auth.PasswordResetRequest;
import com.project.demo.logic.entity.auth.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        // Simular envío de token (debes implementar real en el futuro)
        if (userOptional.isPresent()) {
            String resetToken = UUID.randomUUID().toString();
            System.out.println("Token de recuperación (simulado): " + resetToken + " para usuario: " + request.getEmail());
        }

        return ResponseEntity.ok("If the email exists, a password reset link will be sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email, @RequestParam("newPassword") String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity.ok("Password updated successfully");
    }

}
