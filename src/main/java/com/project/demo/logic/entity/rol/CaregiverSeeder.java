package com.project.demo.logic.entity.rol;

import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.auth.RoleEnum;
import com.project.demo.logic.entity.caregiver.Caregiver;
import com.project.demo.logic.entity.caregiver.CaregiverRole;
import com.project.demo.logic.entity.caregiver.UserCaregiver;
import com.project.demo.logic.entity.caregiver.repository.CaregiverRepository;
import com.project.demo.logic.entity.caregiver.repository.UserCaregiverRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Order(2)
@org.springframework.context.annotation.Profile("!test")
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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedCaregiverUser();
    }

    @Transactional
    protected void seedCaregiverUser() {
        final String email = "caregiver@mentana.com";
        if (userRepository.findByEmail(email).isPresent()) return;

        Optional<Role> roleOpt = roleRepository.findByName(RoleEnum.CAREGIVER);
        if (roleOpt.isEmpty()) return;

        User u = new User();
        u.setName("Caregiver");
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode("caregiver123"));
        u.setRole(roleOpt.get());
        u.setIsCaregiver(true);
        User savedUser = userRepository.save(u);

        Caregiver cg = new Caregiver();
        cg.setName(savedUser.getName());
        cg.setEmail(savedUser.getEmail());
        cg.setPhone("00000000");
        Caregiver savedCg = caregiverRepository.save(cg);

        UserCaregiver uc = new UserCaregiver();
        uc.setUser(savedUser);
        uc.setCaregiver(savedCg);
        uc.setRelationship(CaregiverRole.CAREGIVER);
        userCaregiverRepository.save(uc);
    }
}
