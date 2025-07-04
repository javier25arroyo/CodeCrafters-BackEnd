package com.project.demo.logic.entity.caregiver;
import com.project.demo.logic.entity.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "user_caregivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
