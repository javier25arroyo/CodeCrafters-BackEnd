package com.project.demo.rest.auth;

import com.project.demo.logic.entity.auth.*;
import com.project.demo.logic.entity.caregiver.Caregiver;
import com.project.demo.logic.entity.caregiver.CaregiverRole;
import com.project.demo.logic.entity.caregiver.UserCaregiver;
import com.project.demo.logic.entity.caregiver.repository.UserCaregiverRepository;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.LoginResponse;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.service.CaregiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

/**
 * Controlador REST para gestionar la autenticación y el registro de usuarios.
 * Proporciona endpoints para el inicio de sesión (login) y el registro (signup).
 */
@RequestMapping("/auth")
@RestController
public class AuthRestController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CaregiverService caregiverService;

    @Autowired
    private UserCaregiverRepository userCaregiverRepository;


    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    /**
     * Constructor para la inyección de dependencias del servicio de autenticación y JWT.
     *
     * @param jwtService            Servicio para la generación y validación de tokens JWT.
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
     * @return ResponseEntity con un objeto {@link LoginResponse} que contiene el token JWT y la información del usuario.
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
     * @param req El objeto User con los datos del nuevo usuario a registrar.
     * @return ResponseEntity con el usuario guardado si el registro es exitoso, o un mensaje de error si el email ya está en uso.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setIsCaregiver(req.isCaregiver());

        RoleEnum re = req.isCaregiver() ? RoleEnum.CAREGIVER : RoleEnum.USER;
        Role role = roleRepository.findByName(re).orElseThrow();
        u.setRole(role);

        User saved = userRepository.save(u);

        if (req.isCaregiver()) {
            Caregiver c = new Caregiver();
            c.setName(saved.getName());
            c.setEmail(saved.getEmail());
            c.setPhone(req.getPhone());
            Caregiver savedCg = caregiverService.crear(c);

            UserCaregiver uc = new UserCaregiver();
            uc.setUser(saved);
            uc.setCaregiver(savedCg);
            uc.setRelationship(CaregiverRole.CAREGIVER);
            userCaregiverRepository.save(uc);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Autentica a un usuario utilizando un token de ID de Google.
     *
     * @param request El objeto que contiene el token de ID de Google.
     * @return ResponseEntity con un objeto {@link LoginResponse} que contiene el token JWT y la información del usuario.
     */
    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody GoogleTokenRequest request) {
        try {
            User authenticatedUser = authenticationService.authenticateWithGoogle(request.getIdToken());

            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());
            loginResponse.setAuthUser(authenticatedUser);

            return ResponseEntity.ok(loginResponse);
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Google token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Authentication failed: " + e.getMessage());
        }
    }

}