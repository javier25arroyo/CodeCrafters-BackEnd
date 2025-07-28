package com.project.demo.logic.exceptions;

import java.util.List;

public class ValidationException extends BaseException {
    private static final String ERROR_CODE = "VALIDATION_ERROR";
    private final List<String> validationErrors;
    
    public ValidationException(String message, String operation) {
        super(message, ERROR_CODE, operation);
        this.validationErrors = List.of(message);
    }
    
    public ValidationException(List<String> validationErrors, String operation) {
        super("Validation failed: " + String.join(", ", validationErrors), ERROR_CODE, operation);
        this.validationErrors = validationErrors;
    }
    
    public List<String> getValidationErrors() {
        return validationErrors;
    }
    
    public static class InvalidEmailException extends ValidationException {
        public InvalidEmailException(String email, String operation) {
            super("Invalid email format: " + email, operation);
        }
    }
    
    public static class InvalidPasswordException extends ValidationException {
        public InvalidPasswordException(String reason, String operation) {
            super("Invalid password: " + reason, operation);
        }
    }
    
    public static class RequiredFieldException extends ValidationException {
        public RequiredFieldException(String fieldName, String operation) {
            super("Required field is missing: " + fieldName, operation);
        }
    }
}