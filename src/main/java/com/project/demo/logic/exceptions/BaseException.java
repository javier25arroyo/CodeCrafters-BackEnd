package com.project.demo.logic.exceptions;

import java.time.LocalDateTime;

/**
 * Excepción base para todas las excepciones personalizadas de la aplicación.
 * Proporciona campos comunes como código de error, marca de tiempo y operación.
 */
public abstract class BaseException extends RuntimeException {
    private final String errorCode;
    private final LocalDateTime timestamp;
    private final String operation;

    /**
     * Constructor para BaseException.
     *
     * @param message   Mensaje de error.
     * @param errorCode Código de error.
     * @param operation Operación que causó la excepción.
     */
    public BaseException(String message, String errorCode, String operation) {
        super(message);
        this.errorCode = errorCode;
        this.operation = operation;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructor para BaseException con una causa.
     *
     * @param message   Mensaje de error.
     * @param errorCode Código de error.
     * @param operation Operación que causó la excepción.
     * @param cause     Causa de la excepción.
     */
    public BaseException(String message, String errorCode, String operation, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.operation = operation;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Obtiene el código de error.
     *
     * @return El código de error.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Obtiene la marca de tiempo de la excepción.
     *
     * @return La marca de tiempo.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Obtiene la operación que causó la excepción.
     *
     * @return La operación.
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Obtiene un mensaje detallado de la excepción.
     *
     * @return El mensaje detallado.
     */
    public String getDetailedMessage() {
        return String.format("[%s] %s - Operation: %s, Time: %s",
                errorCode, getMessage(), operation, timestamp);
    }
}
