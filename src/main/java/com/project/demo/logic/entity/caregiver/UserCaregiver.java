package com.project.demo.logic.entity.caregiver;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

/**
 * Representa la relación entre un usuario y un cuidador. Esta entidad mapea la tabla
 * 'user_caregivers' en la base de datos.
 */
@Entity
@Table(name = "user_caregivers")
public class UserCaregiver {
    /** Identificador único de la relación usuario-cuidador. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** El usuario asociado a esta relación. */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** El cuidador asociado a esta relación. */
    @ManyToOne
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver;

    /** El tipo de relación entre el usuario y el cuidador. */
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CaregiverRole relationship;

    /** Constructor por defecto. */
    public UserCaregiver() {}

    /**
     * Constructor para crear una instancia de UserCaregiver con todos los parámetros.
     *
     * @param id Identificador único.
     * @param user Usuario asociado.
     * @param caregiver Cuidador asociado.
     * @param relationship Tipo de relación.
     */
    public UserCaregiver(Integer id, User user, Caregiver caregiver, CaregiverRole relationship) {
        this.id = id;
        this.user = user;
        this.caregiver = caregiver;
        this.relationship = relationship;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }

    public CaregiverRole getRelationship() {
        return relationship;
    }

    public void setRelationship(CaregiverRole relationship) {
        this.relationship = relationship;
    }
}
