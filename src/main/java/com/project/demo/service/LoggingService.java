package com.project.demo.service;

import com.project.demo.logic.exceptions.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * Servicio para el registro centralizado de logs en la aplicación.
 * Utiliza SLF4J y MDC (Mapped Diagnostic Context) para añadir contexto a los logs.
 */
@Service
public class LoggingService {

    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    /**
     * Constructor por defecto.
     */
    public LoggingService() {
    }

    /**
     * Registra un error de aplicación basado en una {@link BaseException}.
     *
     * @param exception  La excepción base que contiene los detalles del error.
     * @param className  El nombre de la clase donde ocurrió el error.
     * @param methodName El nombre del método donde ocurrió el error.
     */
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

    /**
     * Registra un error del sistema o una excepción no controlada.
     *
     * @param exception  La excepción que ocurrió.
     * @param className  El nombre de la clase donde ocurrió el error.
     * @param methodName El nombre del método donde ocurrió el error.
     * @param operation  La operación que se estaba realizando cuando ocurrió el error.
     */
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

    /**
     * Registra una operación de negocio.
     *
     * @param operation El nombre de la operación de negocio.
     * @param details   Detalles adicionales sobre la operación.
     * @param context   Un mapa de contexto con información adicional relevante para la operación.
     */
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

    /**
     * Registra un evento de seguridad.
     *
     * @param event   El tipo de evento de seguridad (ej. "LOGIN_SUCCESS", "LOGIN_FAILED").
     * @param details Detalles adicionales sobre el evento de seguridad.
     * @param userId  El ID del usuario asociado al evento de seguridad (puede ser null si no hay usuario).
     */
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