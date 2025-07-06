package com.project.demo.logic.entity.caregiver;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_caregivers")
public class UserCaregiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CaregiverRole relationship;

    public UserCaregiver() {
    }

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
