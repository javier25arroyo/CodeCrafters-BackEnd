package com.project.demo.logic.entity.rol;

import com.google.api.client.util.Value;
import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.auth.RoleEnum;
import com.project.demo.logic.entity.caregiver.Caregiver;
import com.project.demo.logic.entity.caregiver.CaregiverRole;
import com.project.demo.logic.entity.caregiver.UserCaregiver;
import com.project.demo.logic.entity.caregiver.repository.CaregiverRepository;
import com.project.demo.logic.entity.caregiver.repository.UserCaregiverRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Order(2)
@Profile({"dev","local"})
@ConditionalOnProperty(name="app.seed.caregiver.enabled", havingValue="true")
public class CaregiverSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CaregiverRepository caregiverRepository;
    private final UserCaregiverRepository userCaregiverRepository;
    private final PasswordEncoder passwordEncoder;

    public CaregiverSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            CaregiverRepository caregiverRepository,
            UserCaregiverRepository userCaregiverRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.caregiverRepository = caregiverRepository;
        this.userCaregiverRepository = userCaregiverRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${app.seed.caregiver.email:}")
    private String caregiverEmail;

    @Value("${app.seed.caregiver.password:}")
    private String caregiverPassword;

    @Value("${app.seed.caregiver.phone:}")
    private String caregiverPhone;

    @Value("${app.seed.caregiver.name:Caregiver}")
    private String caregiverName;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedCaregiverUser();
    }

    @Transactional
    protected void seedCaregiverUser() {
        if (caregiverEmail == null || caregiverEmail.isBlank()) return;
        if (userRepository.findByEmail(caregiverEmail).isPresent()) return;

        var roleOpt = roleRepository.findByName(RoleEnum.CAREGIVER);
        if (roleOpt.isEmpty()) return;

        User u = new User();
        u.setName(caregiverName);
        u.setEmail(caregiverEmail);
        u.setPassword(passwordEncoder.encode(caregiverPassword));
        u.setRole(roleOpt.get());
        u.setIsCaregiver(true);
        User savedUser = userRepository.save(u);

        Caregiver cg = new Caregiver();
        cg.setName(savedUser.getName());
        cg.setEmail(savedUser.getEmail());
        cg.setPhone(caregiverPhone);
        Caregiver savedCg = caregiverRepository.save(cg);

        UserCaregiver uc = new UserCaregiver();
        uc.setUser(savedUser);
        uc.setCaregiver(savedCg);
        uc.setRelationship(CaregiverRole.CAREGIVER);
        userCaregiverRepository.save(uc);
    }
}