package com.project.demo.logic.entity.http;

/**
 * Clase genérica que representa una respuesta HTTP estandarizada.
 * Incluye un mensaje, los datos de la respuesta y metadatos adicionales.
 *
 * @param <T> El tipo de los datos contenidos en la respuesta.
 */
public class HttpResponse<T> {
    private String message;
    private T data;

    private Meta meta;

    /**
     * Constructor para crear una respuesta HTTP con solo un mensaje.
     *
     * @param message El mensaje descriptivo de la respuesta.
     */
    public HttpResponse(String message) {
        this.message = message;
    }

    /**
     * Constructor para crear una respuesta HTTP con un mensaje y datos.
     *
     * @param message El mensaje descriptivo de la respuesta.
     * @param data    Los datos a incluir en la respuesta.
     */
    public HttpResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    /**
     * Constructor para crear una respuesta HTTP con un mensaje y metadatos.
     *
     * @param message El mensaje descriptivo de la respuesta.
     * @param meta    Los metadatos de la respuesta.
     */
    public HttpResponse(String message, Meta meta) {
        this.message = message;
        this.meta = meta;
    }

    /**
     * Constructor completo para crear una respuesta HTTP con un mensaje, datos y metadatos.
     *
     * @param message El mensaje descriptivo de la respuesta.
     * @param data    Los datos a incluir en la respuesta.
     * @param meta    Los metadatos de la respuesta.
     */
    public HttpResponse(String message, T data, Meta meta) {
        this.message = message;
        this.data = data;
        this.meta = meta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}