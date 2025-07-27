package com.project.demo.service;

/**
 * Representa una respuesta de mensaje simple. Se utiliza para enviar mensajes de texto genéricos
 * como respuesta desde los endpoints de la API.
 */
public class MessageResponse {
    private String message;

    /**
     * Constructor para crear una nueva instancia de MessageResponse.
     *
     * @param message El mensaje a encapsular en la respuesta.
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Obtiene el mensaje de la respuesta.
     *
     * @return El mensaje de texto.
     */
    public String getMessage() {
        return message;
    }
}
