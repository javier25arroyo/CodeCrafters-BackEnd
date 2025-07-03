package com.project.demo.logic.entity.caregiver;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "caregiver")
public class Caregiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @OneToMany(mappedBy = "caregiver")
    private List<UserCaregiver> userCaregivers;

    public Caregiver() {
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
