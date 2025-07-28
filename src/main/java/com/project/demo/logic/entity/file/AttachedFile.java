package com.project.demo.logic.entity.file;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Representa un archivo adjunto en el sistema.
 * Esta entidad mapea la tabla 'attached_files' en la base de datos.
 */
@Entity
@Table(name = "attached_files")
public class AttachedFile {
    /**
     * Identificador único del archivo adjunto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * URL o ruta donde se almacena el archivo.
     */
    @Column(columnDefinition = "TEXT")
    private String url;

    /**
     * Tipo de archivo (ej. "imagen", "documento", "video").
     */
    @Column(length = 50)
    private String type;

    /**
     * Fecha y hora en que se adjuntó el archivo.
     */
    private LocalDateTime date;

    /**
     * Entidad a la que está relacionado este archivo (ej. "User", "Game").
     */
    @Column(name = "related_entity", length = 50)
    private String relatedEntity;

    /**
     * ID de la entidad a la que está relacionado este archivo.
     */
    @Column(name = "entity_id")
    private Integer entityId;

    /**
     * Constructor por defecto.
     */
    public AttachedFile() {
    }

    /**
     * Constructor para crear una instancia de AttachedFile con todos los parámetros.
     *
     * @param id            Identificador único.
     * @param url           URL o ruta del archivo.
     * @param type          Tipo de archivo.
     * @param date          Fecha de adjunción.
     * @param relatedEntity Entidad relacionada.
     * @param entityId      ID de la entidad relacionada.
     */
    public AttachedFile(Integer id, String url, String type, LocalDateTime date, String relatedEntity, Integer entityId) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.date = date;
        this.relatedEntity = relatedEntity;
        this.entityId = entityId;
    }

    /**
     * Obtiene el ID del archivo adjunto.
     *
     * @return El ID del archivo adjunto.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del archivo adjunto.
     *
     * @param id El nuevo ID del archivo adjunto.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la URL del archivo adjunto.
     *
     * @return La URL del archivo adjunto.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL del archivo adjunto.
     *
     * @param url La nueva URL del archivo adjunto.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene el tipo de archivo adjunto.
     *
     * @return El tipo de archivo adjunto.
     */
    public String getType() {
        return type;
    }

    /**
     * Establece el tipo de archivo adjunto.
     *
     * @param type El nuevo tipo de archivo adjunto.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtiene la fecha de creación del archivo adjunto.
     *
     * @return La fecha de creación del archivo adjunto.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Establece la fecha de creación del archivo adjunto.
     *
     * @param date La nueva fecha de creación del archivo adjunto.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Obtiene la entidad relacionada con el archivo adjunto.
     *
     * @return La entidad relacionada con el archivo adjunto.
     */
    public String getRelatedEntity() {
        return relatedEntity;
    }

    /**
     * Establece la entidad relacionada con el archivo adjunto.
     *
     * @param relatedEntity La nueva entidad relacionada con el archivo adjunto.
     */
    public void setRelatedEntity(String relatedEntity) {
        this.relatedEntity = relatedEntity;
    }

    /**
     * Obtiene el ID de la entidad relacionada con el archivo adjunto.
     *
     * @return El ID de la entidad relacionada con el archivo adjunto.
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * Establece el ID de la entidad relacionada con el archivo adjunto.
     *
     * @param entityId El nuevo ID de la entidad relacionada con el archivo adjunto.
     */
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
