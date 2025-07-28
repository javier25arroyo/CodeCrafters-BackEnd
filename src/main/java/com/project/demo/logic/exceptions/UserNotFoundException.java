package com.project.demo.logic.exceptions;

public class UserNotFoundException extends BaseException {
    private static final String ERROR_CODE = "USER_NOT_FOUND";
    
    public UserNotFoundException(String message, String operation) {
        super(message, ERROR_CODE, operation);
    }
    
    public UserNotFoundException(Long userId, String operation) {
        super("User not found with id: " + userId, ERROR_CODE, operation);
    }
    
    public UserNotFoundException(String email, String operation, boolean isEmail) {
        super("User not found with email: " + email, ERROR_CODE, operation);
    }
}