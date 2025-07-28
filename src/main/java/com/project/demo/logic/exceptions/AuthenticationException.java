package com.project.demo.logic.exceptions;

public class AuthenticationException extends BaseException {
    private static final String ERROR_CODE = "AUTH_ERROR";
    
    public AuthenticationException(String message, String operation) {
        super(message, ERROR_CODE, operation);
    }
    
    public AuthenticationException(String message, String operation, Throwable cause) {
        super(message, ERROR_CODE, operation, cause);
    }
    
    public static class InvalidCredentialsException extends AuthenticationException {
        public InvalidCredentialsException(String operation) {
            super("Invalid username or password", operation);
        }
    }
    
    public static class TokenExpiredException extends AuthenticationException {
        public TokenExpiredException(String operation) {
            super("Authentication token has expired", operation);
        }
    }
    
    public static class InvalidTokenException extends AuthenticationException {
        public InvalidTokenException(String operation) {
            super("Invalid authentication token", operation);
        }
    }
}