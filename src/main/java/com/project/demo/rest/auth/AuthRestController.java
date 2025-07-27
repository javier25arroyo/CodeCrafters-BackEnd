package com.project.demo.rest.auth;

import com.project.demo.logic.entity.auth.AuthenticationService;
import com.project.demo.logic.entity.auth.GoogleTokenRequest;
import com.project.demo.logic.entity.auth.JwtService;
import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.auth.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.LoginResponse;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gestionar la autenticación y el registro de usuarios. Proporciona endpoints
 * para el inicio de sesión (login) y el registro (signup).
 */
@RequestMapping("/auth")
@RestController
public class AuthRestController {

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private RoleRepository roleRepository;

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    /**
     * Constructor para la inyección de dependencias del servicio de autenticación y JWT.
     *
     * @param jwtService Servicio para la generación y validación de tokens JWT.
     * @param authenticationService Servicio para la lógica de autenticación de usuarios.
     */
    public AuthRestController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * Autentica a un usuario y devuelve un token JWT si las credenciales son válidas.
     *
     * @param user El objeto User con las credenciales (email y contraseña) para autenticar.
     * @return ResponseEntity con un objeto {@link LoginResponse} que contiene el token JWT y la
     *     información del usuario.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody User user) {
        User authenticatedUser = authenticationService.authenticate(user);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        Optional<User> foundedUser = userRepository.findByEmail(user.getEmail());

        foundedUser.ifPresent(loginResponse::setAuthUser);

        return ResponseEntity.ok(loginResponse);
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param user El objeto User con los datos del nuevo usuario a registrar.
     * @return ResponseEntity con el usuario guardado si el registro es exitoso, o un mensaje de
     *     error si el email ya está en uso.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found");
        }
        user.setRole(optionalRole.get());
        user.setEnabled(true); // Set user as enabled by default
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody GoogleTokenRequest request) {
        try {
            User authenticatedUser =
                    authenticationService.authenticateWithGoogle(request.getIdToken());

            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());
            loginResponse.setAuthUser(authenticatedUser);

            return ResponseEntity.ok(loginResponse);
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Google token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Authentication failed: " + e.getMessage());
        }
    }
}
