package com.project.demo.logic.entity.auth;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.auth.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.exceptions.BusinessException;
import com.project.demo.service.LoggingService;
import com.project.demo.service.PasswordService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

/**
 * Servicio para la autenticación de usuarios.
 * Gestiona el proceso de autenticación utilizando Spring Security.
 */
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final AuthenticationManager authenticationManager;
    private final GoogleAuthService googleAuthService;
    private final RoleRepository roleRepository;
    private final LoggingService loggingService;

    /**
     * Constructor para la inyección de dependencias.
     *
     * @param userRepository        Repositorio para la gestión de usuarios.
     * @param authenticationManager Gestor de autenticación de Spring Security.
     * @param passwordService       Servicio para el manejo de contraseñas.
     * @param googleAuthService     Servicio para la autenticación con Google.
     * @param roleRepository        Repositorio para la gestión de roles.
     * @param loggingService        Servicio de logging.
     */
    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordService passwordService,
            GoogleAuthService googleAuthService,
            RoleRepository roleRepository,
            LoggingService loggingService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.googleAuthService = googleAuthService;
        this.roleRepository = roleRepository;
        this.loggingService = loggingService;
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

    /**
     * Autentica a un usuario utilizando un token de ID de Google.
     *
     * @param idToken El token de ID de Google.
     * @return El objeto {@link User} autenticado.
     * @throws GeneralSecurityException Si ocurre un error de seguridad general.
     * @throws IOException              Si ocurre un error de entrada/salida.
     */
    public User authenticateWithGoogle(String idToken) throws GeneralSecurityException, IOException {
        GoogleIdToken.Payload payload = googleAuthService.verifyToken(idToken);

        String email = payload.getEmail();
        String googleId = payload.getSubject();
        String name = (String) payload.get("name");

        return userRepository.findByEmail(email)
                .map(user -> updateExistingUserWithGoogleId(user, googleId))
                .orElseGet(() -> createNewGoogleUser(email, name, googleId));
    }
    
    private User updateExistingUserWithGoogleId(User user, String googleId) {
        if (user.getGoogleId() == null) {
            user.setGoogleId(googleId);
            return userRepository.save(user);
        }
        return user;
    }
    
    private User createNewGoogleUser(String email, String name, String googleId) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setGoogleId(googleId);
        newUser.setPassword(passwordService.encode("GOOGLE_AUTH_" + googleId));
        
        Role userRole = roleRepository.findByName(RoleEnum.USER)
            .orElseThrow(() -> new BusinessException.RoleNotFoundException("USER", "createNewGoogleUser"));
        newUser.setRole(userRole);
        
        return userRepository.save(newUser);
    }
}
