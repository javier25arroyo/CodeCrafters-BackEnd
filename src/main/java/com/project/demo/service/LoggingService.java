package com.project.demo.service;

import com.project.demo.logic.exceptions.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class LoggingService {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);
    
    public void logError(BaseException exception, String className, String methodName) {
        String errorId = UUID.randomUUID().toString();
        
        try {
            MDC.put("errorId", errorId);
            MDC.put("errorCode", exception.getErrorCode());
            MDC.put("operation", exception.getOperation());
            MDC.put("className", className);
            MDC.put("methodName", methodName);
            MDC.put("timestamp", exception.getTimestamp().toString());
            
            logger.error("Application Error [{}]: {} | Class: {} | Method: {} | Operation: {} | Time: {}", 
                    errorId,
                    exception.getMessage(),
                    className,
                    methodName,
                    exception.getOperation(),
                    exception.getTimestamp(),
                    exception);
                    
        } finally {
            MDC.clear();
        }
    }
    
    public void logError(Exception exception, String className, String methodName, String operation) {
        String errorId = UUID.randomUUID().toString();
        
        try {
            MDC.put("errorId", errorId);
            MDC.put("className", className);
            MDC.put("methodName", methodName);
            MDC.put("operation", operation);
            
            logger.error("System Error [{}]: {} | Class: {} | Method: {} | Operation: {}", 
                    errorId,
                    exception.getMessage(),
                    className,
                    methodName,
                    operation,
                    exception);
                    
        } finally {
            MDC.clear();
        }
    }
    
    public void logBusinessOperation(String operation, String details, Map<String, Object> context) {
        try {
            String operationId = UUID.randomUUID().toString();
            MDC.put("operationId", operationId);
            MDC.put("operation", operation);
            
            if (context != null) {
                context.forEach((key, value) -> MDC.put(key, value.toString()));
            }
            
            logger.info("Business Operation [{}]: {} | Details: {}", operationId, operation, details);
            
        } finally {
            MDC.clear();
        }
    }
    
    public void logSecurityEvent(String event, String details, String userId) {
        try {
            String eventId = UUID.randomUUID().toString();
            MDC.put("eventId", eventId);
            MDC.put("securityEvent", event);
            MDC.put("userId", userId != null ? userId : "anonymous");
            
            logger.warn("Security Event [{}]: {} | Details: {} | User: {}", 
                    eventId, event, details, userId != null ? userId : "anonymous");
                    
        } finally {
            MDC.clear();
        }
    }
}