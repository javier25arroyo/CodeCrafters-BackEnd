package com.project.demo.logic.entity.caregiver;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

/**
 * Representa la relación entre un usuario y un cuidador.
 * Esta entidad mapea la tabla 'user_caregivers' en la base de datos.
 */
@Entity
@Table(name = "user_caregivers")
public class UserCaregiver {
    /**
     * Identificador único de la relación usuario-cuidador.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El usuario asociado a esta relación.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * El cuidador asociado a esta relación.
     */
    @ManyToOne
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver;

    /**
     * El tipo de relación entre el usuario y el cuidador.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CaregiverRole relationship;

    /**
     * Constructor por defecto.
     */
    public UserCaregiver() {
    }

    /**
     * Constructor para crear una instancia de UserCaregiver con todos los parámetros.
     *
     * @param id           Identificador único.
     * @param user         Usuario asociado.
     * @param caregiver    Cuidador asociado.
     * @param relationship Tipo de relación.
     */
    public UserCaregiver(Integer id, User user, Caregiver caregiver, CaregiverRole relationship) {
        this.id = id;
        this.user = user;
        this.caregiver = caregiver;
        this.relationship = relationship;
    }

    /**
     * Obtiene el ID de la relación usuario-cuidador.
     *
     * @return El ID de la relación usuario-cuidador.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID de la relación usuario-cuidador.
     *
     * @param id El nuevo ID de la relación usuario-cuidador.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario asociado a esta relación.
     *
     * @return El usuario asociado a esta relación.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario asociado a esta relación.
     *
     * @param user El nuevo usuario asociado a esta relación.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene el cuidador asociado a esta relación.
     *
     * @return El cuidador asociado a esta relación.
     */
    public Caregiver getCaregiver() {
        return caregiver;
    }

    /**
     * Establece el cuidador asociado a esta relación.
     *
     * @param caregiver El nuevo cuidador asociado a esta relación.
     */
    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }

    /**
     * Obtiene el tipo de relación entre el usuario y el cuidador.
     *
     * @return El tipo de relación entre el usuario y el cuidador.
     */
    public CaregiverRole getRelationship() {
        return relationship;
    }

    /**
     * Establece el tipo de relación entre el usuario y el cuidador.
     *
     * @param relationship El nuevo tipo de relación entre el usuario y el cuidador.
     */
    public void setRelationship(CaregiverRole relationship) {
        this.relationship = relationship;
    }
}
