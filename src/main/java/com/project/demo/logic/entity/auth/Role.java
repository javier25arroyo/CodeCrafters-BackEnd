package com.project.demo.logic.entity.auth;

import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Representa un rol de usuario en el sistema. Esta entidad mapea la tabla 'roles' en la base de
 * datos.
 */
@Table(name = "roles")
@Entity
public class Role {
    /** Identificador único del rol. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    /** Nombre único del rol, representado por la enumeración {@link RoleEnum}. */
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    /** Descripción del rol. */
    @Column(nullable = false)
    private String description;

    /** Marca de tiempo de creación del rol. Se genera automáticamente. */
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    /** Marca de tiempo de la última actualización del rol. Se actualiza automáticamente. */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    /** Constructor por defecto. */
    public Role() {}

    /**
     * Constructor para crear una instancia de Role con todos los parámetros.
     *
     * @param id Identificador único.
     * @param name Nombre del rol.
     * @param description Descripción del rol.
     * @param createdAt Fecha de creación.
     * @param updatedAt Fecha de última actualización.
     */
    public Role(Integer id, RoleEnum name, String description, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
