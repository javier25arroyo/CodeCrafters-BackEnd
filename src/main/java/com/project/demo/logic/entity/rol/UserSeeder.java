package com.project.demo.logic.entity.rol;

import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.auth.RoleEnum;
import com.project.demo.logic.entity.auth.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Order(1)
@Component
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent>{
        private final RoleRepository roleRepository;
        private final UserRepository userRepository;

        private final PasswordEncoder passwordEncoder;

        public UserSeeder(
                RoleRepository roleRepository,
                UserRepository userRepository,
                PasswordEncoder passwordEncoder
        ) {
                this.roleRepository = roleRepository;
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
        }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createUser();
    }

    private void createUser() {
        User user = new User();
        user.setName("Code");
        user.setLastname("Crafters");
        user.setEmail("codecrafters@gmail.com");
        user.setPassword("123456");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var userToCreate = new User();
        userToCreate.setName(user.getName());
        userToCreate.setLastname(user.getLastname());
        userToCreate.setEmail(user.getEmail());
        userToCreate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToCreate.setRole(optionalRole.get());

        userRepository.save(userToCreate);
    }
}
