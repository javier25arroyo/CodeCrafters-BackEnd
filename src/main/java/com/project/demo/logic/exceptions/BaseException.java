package com.project.demo.logic.exceptions;

import java.time.LocalDateTime;

public abstract class BaseException extends RuntimeException {
    private final String errorCode;
    private final LocalDateTime timestamp;
    private final String operation;
    
    public BaseException(String message, String errorCode, String operation) {
        super(message);
        this.errorCode = errorCode;
        this.operation = operation;
        this.timestamp = LocalDateTime.now();
    }
    
    public BaseException(String message, String errorCode, String operation, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.operation = operation;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public String getDetailedMessage() {
        return String.format("[%s] %s - Operation: %s, Time: %s", 
                errorCode, getMessage(), operation, timestamp);
    }
}