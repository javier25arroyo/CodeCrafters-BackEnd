package com.project.demo.logic.entity.caregiver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

/**
 * Representa un cuidador en el sistema.
 * Esta entidad mapea la tabla 'caregivers' en la base de datos.
 */
@Entity
@Table(name = "caregivers")
public class Caregiver {
    /**
     * Identificador único del cuidador.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del cuidador.
     */
    @Column(length = 100)
    private String name;

    /**
     * Correo electrónico del cuidador.
     */
    @Column(length = 100)
    private String email;

    /**
     * Número de teléfono del cuidador.
     */
    @Column(length = 20)
    private String phone;

    /**
     * Lista de relaciones entre este cuidador y los usuarios.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "caregiver")
    private List<UserCaregiver> userCaregivers;

    /**
     * Constructor por defecto.
     */
    public Caregiver() {
    }

    /**
     * Constructor para crear una instancia de Caregiver con todos los parámetros.
     *
     * @param id             Identificador único.
     * @param name           Nombre del cuidador.
     * @param email          Correo electrónico del cuidador.
     * @param phone          Número de teléfono del cuidador.
     * @param userCaregivers Lista de relaciones usuario-cuidador.
     */
    public Caregiver(Integer id, String name, String email, String phone, List<UserCaregiver> userCaregivers) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userCaregivers = userCaregivers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<UserCaregiver> getUserCaregivers() {
        return userCaregivers;
    }

    public void setUserCaregivers(List<UserCaregiver> userCaregivers) {
        this.userCaregivers = userCaregivers;
    }
}
