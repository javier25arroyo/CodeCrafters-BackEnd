package com.project.demo.logic.entity.rol;

import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.auth.RoleEnum;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Componente encargado de sembrar los roles iniciales en la base de datos al iniciar la aplicación.
 * Implementa {@link ApplicationListener} para escuchar el evento {@link ContextRefreshedEvent}.
 */
@Component
@Order(0)
@org.springframework.context.annotation.Profile("!test")
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    /**
     * Constructor para la inyección de dependencias.
     *
     * @param roleRepository Repositorio para la gestión de roles.
     */
    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Método que se ejecuta cuando el contexto de la aplicación ha sido refrescado. Llama al método
     * para cargar los roles iniciales.
     *
     * @param contextRefreshedEvent El evento de refresco del contexto.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    /** Carga los roles predefinidos (USER, SUPER_ADMIN) en la base de datos si no existen. */
    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] {RoleEnum.USER, RoleEnum.SUPER_ADMIN};
        Map<RoleEnum, String> roleDescriptionMap =
                Map.of(
                        RoleEnum.USER, "Default user role",
                        RoleEnum.SUPER_ADMIN, "Super Administrator role");

        Arrays.stream(roleNames)
                .forEach(
                        (roleName) -> {
                            Optional<Role> optionalRole = roleRepository.findByName(roleName);

                            optionalRole.ifPresentOrElse(
                                    System.out::println,
                                    () -> {
                                        Role roleToCreate = new Role();

                                        roleToCreate.setName(roleName);
                                        roleToCreate.setDescription(
                                                roleDescriptionMap.get(roleName));

                                        roleRepository.save(roleToCreate);
                                    });
                        });
    }
}
