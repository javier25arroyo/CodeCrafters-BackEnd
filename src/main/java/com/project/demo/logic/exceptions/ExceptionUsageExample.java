package com.project.demo.logic.exceptions;

import com.project.demo.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Ejemplos de uso de las excepciones personalizadas del sistema.
 * Esta clase muestra cómo implementar manejo de errores efectivo.
 */
@Component
public class ExceptionUsageExample {
    /**
     * Constructor por defecto.
     */
    public ExceptionUsageExample() {
    }

    @Autowired
    private LoggingService loggingService;
    
    /**
     * Ejemplo de validación con excepción específica
     *
     * @param email El email a validar.
     * @param password La contraseña a validar.
     */
    public void validateUserInput(String email, String password) {
        // Validación de email
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException.RequiredFieldException("email", "validateUserInput");
        }
        
        if (!email.contains("@")) {
            throw new ValidationException.InvalidEmailException(email, "validateUserInput");
        }
        
        // Validación de contraseña
        if (password == null || password.length() < 8) {
            throw new ValidationException.InvalidPasswordException("must be at least 8 characters", "validateUserInput");
        }
    }
    
    /**
     * Ejemplo de manejo de operaciones de negocio
     *
     * @param userId El ID del usuario.
     */
    public void performBusinessOperation(Long userId) {
        try {
            // Simular operación de negocio
            if (userId == null) {
                throw new UserNotFoundException("User ID cannot be null", "performBusinessOperation");
            }
            
            // Simular fallo de permisos
            if (userId == 999L) {
                throw new BusinessException.InsufficientPermissionsException("performBusinessOperation");
            }
            
            // Operación exitosa
            loggingService.logBusinessOperation("PERFORM_BUSINESS_OP", 
                "Operation completed successfully", 
                java.util.Map.of("userId", userId));
                
        } catch (BaseException e) {
            // Las excepciones personalizadas se propagan automáticamente al GlobalExceptionHandler
            throw e;
        } catch (Exception e) {
            // Wrappear excepciones no controladas
            loggingService.logError(e, "ExceptionUsageExample", "performBusinessOperation", "BUSINESS_OPERATION");
            throw new BusinessException("Unexpected error during business operation", "performBusinessOperation", e);
        }
    }
    
    /**
     * Ejemplo de autenticación con logging de seguridad
     *
     * @param username El nombre de usuario.
     * @param password La contraseña.
     */
    public void authenticateUser(String username, String password) {
        try {
            if (username == null || password == null) {
                loggingService.logSecurityEvent("AUTHENTICATION_FAILED", 
                    "Missing credentials", username);
                throw new AuthenticationException.InvalidCredentialsException("authenticateUser");
            }
            
            // Simular fallo de autenticación
            if ("invalid".equals(username)) {
                loggingService.logSecurityEvent("AUTHENTICATION_FAILED", 
                    "Invalid username", username);
                throw new AuthenticationException.InvalidCredentialsException("authenticateUser");
            }
            
            // Autenticación exitosa
            loggingService.logSecurityEvent("AUTHENTICATION_SUCCESS", 
                "User authenticated successfully", username);
                
        } catch (AuthenticationException e) {
            throw e; // Propagar excepciones de autenticación
        }
    }
}