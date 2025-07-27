package com.project.demo.logic.entity.rol;

import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.auth.RoleEnum;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Componente encargado de sembrar un usuario Super Administrador en la base de datos al iniciar la
 * aplicación. Implementa {@link ApplicationListener} para escuchar el evento {@link
 * ContextRefreshedEvent}.
 */
@Component
@Order(2)
@org.springframework.context.annotation.Profile("!test")
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
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
    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método que se ejecuta cuando el contexto de la aplicación ha sido refrescado. Llama al método
     * para crear el usuario Super Administrador.
     *
     * @param contextRefreshedEvent El evento de refresco del contexto.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    /**
     * Crea un usuario Super Administrador por defecto si no existe ya en la base de datos. El
     * usuario tendrá el rol de {@link RoleEnum#SUPER_ADMIN}.
     */
    private void createSuperAdministrator() {
        User superAdmin = new User();
        superAdmin.setName("Super");
        superAdmin.setEmail("super.admin@gmail.com");
        superAdmin.setPassword("superadmin123");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(superAdmin.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User();
        user.setName(superAdmin.getName());
        user.setEmail(superAdmin.getEmail());
        user.setPassword(passwordEncoder.encode(superAdmin.getPassword()));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}
