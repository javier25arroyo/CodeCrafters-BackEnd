package com.project.demo.logic.entity.caregiver;
import com.project.demo.logic.entity.auth.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_caregiver")
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

    @Column(length = 50)
    private String relationship;
}
