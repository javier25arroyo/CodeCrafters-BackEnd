package com.project.demo.logic.entity.auth;

import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Clase de configuración para la aplicación, que define beans relacionados con la seguridad y autenticación.
 */
@Configuration
public class ApplicationConfiguration {

    @Autowired
    private final UserRepository userRepository;

    /**
     * Constructor para la inyección de dependencias del repositorio de usuarios.
     *
     * @param userRepository Repositorio para la gestión de usuarios.
     */
    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Define un bean {@link UserDetailsService} que carga los detalles del usuario por su correo electrónico.
     *
     * @return Una implementación de {@link UserDetailsService}.
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Define un bean {@link BCryptPasswordEncoder} para la codificación de contraseñas.
     *
     * @return Una instancia de {@link BCryptPasswordEncoder}.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define un bean {@link AuthenticationManager} para gestionar la autenticación.
     *
     * @param config La configuración de autenticación.
     * @return Una instancia de {@link AuthenticationManager}.
     * @throws Exception Si ocurre un error al obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define un bean {@link AuthenticationProvider} que utiliza {@link DaoAuthenticationProvider}
     * para la autenticación basada en la base de datos.
     *
     * @return Una instancia de {@link AuthenticationProvider}.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
