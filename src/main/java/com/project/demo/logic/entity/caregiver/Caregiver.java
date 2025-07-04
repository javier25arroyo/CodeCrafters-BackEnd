package com.project.demo.logic.entity.caregiver;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "caregivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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

    @JsonIgnore
    @OneToMany(mappedBy = "caregiver")
    private List<UserCaregiver> userCaregivers;

    

    
}
