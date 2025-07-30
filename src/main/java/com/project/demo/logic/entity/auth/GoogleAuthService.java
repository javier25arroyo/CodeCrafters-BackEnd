package com.project.demo.logic.entity.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Servicio para la autenticación de usuarios a través de Google.
 * Verifica los tokens de ID de Google y extrae la información del payload.
 */
@Service
public class GoogleAuthService {

    @Value("${google.client.id}")
    private String googleClientId;

    /**
     * Constructor por defecto.
     */
    public GoogleAuthService() {
    }

    /**
     * Verifica un token de ID de Google.
     *
     * @param idTokenString El token de ID de Google como cadena.
     * @return El payload del token de ID de Google si es válido.
     * @throws GeneralSecurityException Si ocurre un error de seguridad general.
     * @throws IOException              Si ocurre un error de entrada/salida.
     * @throws IllegalArgumentException Si el token de ID es inválido.
     */
    public GoogleIdToken.Payload verifyToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            return idToken.getPayload();
        } else {
            throw new IllegalArgumentException("Invalid ID token");
        }
    }
}