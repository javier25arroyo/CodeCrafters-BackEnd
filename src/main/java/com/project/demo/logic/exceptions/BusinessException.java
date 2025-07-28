package com.project.demo.logic.exceptions;

public class BusinessException extends BaseException {
    private static final String ERROR_CODE = "BUSINESS_ERROR";
    
    public BusinessException(String message, String operation) {
        super(message, ERROR_CODE, operation);
    }
    
    public BusinessException(String message, String operation, Throwable cause) {
        super(message, ERROR_CODE, operation, cause);
    }
    
    public static class RoleNotFoundException extends BusinessException {
        public RoleNotFoundException(String roleName, String operation) {
            super("Role not found: " + roleName, operation);
        }
    }
    
    public static class EmailServiceException extends BusinessException {
        public EmailServiceException(String operation, Throwable cause) {
            super("Failed to send email", operation, cause);
        }
    }
    
    public static class UserAlreadyExistsException extends BusinessException {
        public UserAlreadyExistsException(String email, String operation) {
            super("User already exists with email: " + email, operation);
        }
    }
    
    public static class InsufficientPermissionsException extends BusinessException {
        public InsufficientPermissionsException(String operation) {
            super("Insufficient permissions for this operation", operation);
        }
    }
}