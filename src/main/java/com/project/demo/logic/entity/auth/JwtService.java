package com.project.demo.logic.entity.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Servicio para la gestión de tokens JWT (JSON Web Token).
 * Proporciona métodos para generar, validar y extraer información de los tokens JWT.
 */
@Service
public class JwtService {
    /**
     * Clave secreta utilizada para firmar y verificar los tokens JWT.
     * Se inyecta desde las propiedades de configuración.
     */
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    /**
     * Tiempo de expiración de los tokens JWT en milisegundos.
     * Se inyecta desde las propiedades de configuración.
     */
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     * Constructor por defecto.
     */
    public JwtService() {
    }

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     *
     * @param token El token JWT del que se extraerá el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae un "claim" específico de un token JWT.
     *
     * @param token        El token JWT del que se extraerá el claim.
     * @param claimsResolver Una función que resuelve el claim deseado a partir de los claims del token.
     * @param <T>          El tipo del claim a extraer.
     * @return El valor del claim extraído.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un token JWT para un usuario.
     *
     * @param userDetails Los detalles del usuario para el que se generará el token.
     * @return El token JWT generado.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token JWT con claims adicionales para un usuario.
     *
     * @param extraClaims Claims adicionales a incluir en el token.
     * @param userDetails Los detalles del usuario para el que se generará el token.
     * @return El token JWT generado.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Obtiene el tiempo de expiración configurado para los tokens JWT.
     *
     * @return El tiempo de expiración en milisegundos.
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Construye el token JWT.
     *
     * @param extraClaims Claims adicionales.
     * @param userDetails Detalles del usuario.
     * @param expiration  Tiempo de expiración del token.
     * @return El token JWT construido.
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida si un token JWT es válido para un usuario dado.
     *
     * @param token       El token JWT a validar.
     * @param userDetails Los detalles del usuario con los que se validará el token.
     * @return {@code true} si el token es válido, {@code false} en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica si un token JWT ha expirado.
     *
     * @param token El token JWT a verificar.
     * @return {@code true} si el token ha expirado, {@code false} en caso contrario.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración de un token JWT.
     *
     * @param token El token JWT del que se extraerá la fecha de expiración.
     * @return La fecha de expiración.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae todos los claims de un token JWT.
     *
     * @param token El token JWT del que se extraerán los claims.
     * @return Un objeto {@link Claims} que contiene todos los claims del token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene la clave de firma utilizada para los tokens JWT.
     * Decodifica la clave secreta de Base64 y la convierte en un objeto {@link Key}.
     *
     * @return La clave de firma.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

