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

    /**
     * Obtiene el ID del cuidador.
     *
     * @return El ID del cuidador.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del cuidador.
     *
     * @param id El nuevo ID del cuidador.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del cuidador.
     *
     * @return El nombre del cuidador.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del cuidador.
     *
     * @param name El nuevo nombre del cuidador.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el email del cuidador.
     *
     * @return El email del cuidador.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del cuidador.
     *
     * @param email El nuevo email del cuidador.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el teléfono del cuidador.
     *
     * @return El teléfono del cuidador.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Establece el teléfono del cuidador.
     *
     * @param phone El nuevo teléfono del cuidador.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Obtiene la lista de cuidadores de usuario.
     *
     * @return La lista de cuidadores de usuario.
     */
    public List<UserCaregiver> getUserCaregivers() {
        return userCaregivers;
    }

    /**
     * Establece la lista de cuidadores de usuario.
     *
     * @param userCaregivers La nueva lista de cuidadores de usuario.
     */
    public void setUserCaregivers(List<UserCaregiver> userCaregivers) {
        this.userCaregivers = userCaregivers;
    }
}
