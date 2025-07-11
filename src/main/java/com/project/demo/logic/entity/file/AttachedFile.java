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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getRelatedEntity() {
        return relatedEntity;
    }

    public void setRelatedEntity(String relatedEntity) {
        this.relatedEntity = relatedEntity;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
