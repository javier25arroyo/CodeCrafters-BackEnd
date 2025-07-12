package com.project.demo.logic.entity.auth;


import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio para la autenticación de usuarios.
 * Gestiona el proceso de autenticación utilizando Spring Security.
 */
@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    /**
     * Constructor para la inyección de dependencias.
     *
     * @param userRepository        Repositorio para la gestión de usuarios.
     * @param authenticationManager Gestor de autenticación de Spring Security.
     * @param passwordEncoder       Codificador de contraseñas.
     */
    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Autentica a un usuario utilizando su correo electrónico y contraseña.
     *
     * @param input Objeto {@link User} que contiene el correo electrónico y la contraseña del usuario.
     * @return El objeto {@link User} autenticado.
     * @throws org.springframework.security.core.AuthenticationException Si la autenticación falla.
     */
    public User authenticate(User input) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
