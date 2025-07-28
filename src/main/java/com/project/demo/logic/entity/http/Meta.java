package com.project.demo.logic.entity.http;

/**
 * Clase que representa metadatos de una respuesta HTTP, comúnmente utilizada para paginación.
 * Contiene información sobre el método de la petición, la URL, y detalles de paginación.
 */
public class Meta {
    private String method;
    private String url;
    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int pageSize;

    /**
     * Constructor por defecto.
     */
    public Meta() {
    }

    /**
     * Constructor completo para crear una instancia de Meta con todos los detalles de paginación.
     *
     * @param method        El método HTTP de la petición (ej. "GET", "POST").
     * @param url           La URL de la petición.
     * @param totalPages    El número total de páginas disponibles.
     * @param totalElements El número total de elementos disponibles.
     * @param pageNumber    El número de la página actual (basado en 0).
     * @param pageSize      El tamaño de la página (número de elementos por página).
     */
    public Meta(String method, String url, int totalPages, long totalElements, int pageNumber, int pageSize) {
        this.method = method;
        this.url = url;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * Constructor para crear una instancia de Meta con solo el método y la URL.
     *
     * @param method El método HTTP de la petición.
     * @param url    La URL de la petición.
     */
    public Meta(String method, String url) {
        this.method = method;
        this.url = url;
    }

    /**
     * Obtiene el método HTTP de la petición.
     *
     * @return El método HTTP.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Establece el método HTTP de la petición.
     *
     * @param method El nuevo método HTTP.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Obtiene la URL de la petición.
     *
     * @return La URL de la petición.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL de la petición.
     *
     * @param url La nueva URL de la petición.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene el número total de páginas disponibles.
     *
     * @return El número total de páginas.
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Establece el número total de páginas disponibles.
     *
     * @param totalPages El nuevo número total de páginas.
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Obtiene el número total de elementos disponibles.
     *
     * @return El número total de elementos.
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Establece el número total de elementos disponibles.
     *
     * @param totalElements El nuevo número total de elementos.
     */
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * Obtiene el número de la página actual.
     *
     * @return El número de la página actual.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Establece el número de la página actual.
     *
     * @param pageNumber El nuevo número de la página actual.
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Obtiene el tamaño de la página.
     *
     * @return El tamaño de la página.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Establece el tamaño de la página.
     *
     * @param pageSize El nuevo tamaño de la página.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
