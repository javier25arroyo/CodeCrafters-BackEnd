package com.project.demo.logic.entity.rol;

import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.auth.RoleEnum;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Componente encargado de sembrar un usuario inicial en la base de datos al iniciar la aplicación.
 * Implementa {@link ApplicationListener} para escuchar el evento {@link ContextRefreshedEvent}.
 */
@Order(1)
@Component
@org.springframework.context.annotation.Profile("!test")
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent>{
        private final RoleRepository roleRepository;
        private final UserRepository userRepository;

        private final PasswordEncoder passwordEncoder;

        /**
         * Constructor para la inyección de dependencias.
         *
         * @param roleRepository Repositorio para la gestión de roles.
         * @param userRepository Repositorio para la gestión de usuarios.
         * @param passwordEncoder Codificador de contraseñas.
         */
        public UserSeeder(
                RoleRepository roleRepository,
                UserRepository userRepository,
                PasswordEncoder passwordEncoder
        ) {
                this.roleRepository = roleRepository;
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
        }

    /**
     * Método que se ejecuta cuando el contexto de la aplicación ha sido refrescado.
     * Llama al método para crear el usuario inicial.
     *
     * @param contextRefreshedEvent El evento de refresco del contexto.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createUser();
    }

    /**
     * Crea un usuario por defecto si no existe ya en la base de datos.
     * El usuario tendrá el rol de {@link RoleEnum#USER}.
     */
    private void createUser() {
        User user = new User();
        user.setName("Code");
        user.setEmail("codecrafters@gmail.com");
        user.setPassword("superadmin123");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var userToCreate = new User();
        userToCreate.setName(user.getName());
        userToCreate.setEmail(user.getEmail());
        userToCreate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToCreate.setRole(optionalRole.get());

        userRepository.save(userToCreate);
    }
}

