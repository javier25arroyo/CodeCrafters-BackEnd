package com.project.demo.logic.entity.auth;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * Representa un rol de usuario en el sistema.
 * Esta entidad mapea la tabla 'roles' en la base de datos.
 */
@Table(name = "roles")
@Entity
public class Role {
    /**
     * Identificador único del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    /**
     * Nombre único del rol, representado por la enumeración {@link RoleEnum}.
     */
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    /**
     * Descripción del rol.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Marca de tiempo de creación del rol. Se genera automáticamente.
     */
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    /**
     * Marca de tiempo de la última actualización del rol. Se actualiza automáticamente.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * Constructor por defecto.
     */
    public Role() {
    }

    /**
     * Constructor para crear una instancia de Role con todos los parámetros.
     *
     * @param id          Identificador único.
     * @param name        Nombre del rol.
     * @param description Descripción del rol.
     * @param createdAt   Fecha de creación.
     * @param updatedAt   Fecha de última actualización.
     */
    public Role(Integer id, RoleEnum name, String description, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Obtiene el ID del rol.
     *
     * @return El ID del rol.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del rol.
     *
     * @param id El nuevo ID del rol.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del rol.
     *
     * @return El nombre del rol.
     */
    public RoleEnum getName() {
        return name;
    }

    /**
     * Establece el nombre del rol.
     *
     * @param name El nuevo nombre del rol.
     */
    public void setName(RoleEnum name) {
        this.name = name;
    }

    /**
     * Obtiene la descripción del rol.
     *
     * @return La descripción del rol.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece la descripción del rol.
     *
     * @param description La nueva descripción del rol.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene la fecha de creación del rol.
     *
     * @return La fecha de creación del rol.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Establece la fecha de creación del rol.
     *
     * @param createdAt La nueva fecha de creación del rol.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Obtiene la fecha de la última actualización del rol.
     *
     * @return La fecha de la última actualización del rol.
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Establece la fecha de la última actualización del rol.
     *
     * @param updatedAt La nueva fecha de la última actualización del rol.
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
