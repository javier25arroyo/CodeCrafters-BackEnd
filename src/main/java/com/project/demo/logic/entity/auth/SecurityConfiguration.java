package com.project.demo.logic.entity.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad principal para la aplicación.
 * Habilita la seguridad web y la seguridad a nivel de método, y define la cadena de filtros de seguridad.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CoopHeaderFilter coopHeaderFilter;
    
    @Value("${app.frontend.url}")
    private String frontendUrl;
    
    @Value("${app.frontend.login-path}")
    private String loginPath;
    /**
     * Constructor para inyectar las dependencias del proveedor de autenticación, el filtro JWT y el filtro COOP.
     *
     * @param jwtAuthenticationFilter Filtro para la autenticación basada en JWT.
     * @param authenticationProvider  Proveedor que gestiona la autenticación de los usuarios.
     * @param coopHeaderFilter        Filtro para las cabeceras COOP.
     */
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider, CoopHeaderFilter coopHeaderFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.coopHeaderFilter = coopHeaderFilter;
    }

    /**
     * Define la cadena de filtros de seguridad que se aplicará a las peticiones HTTP.
     *
     * @param http El objeto {@link HttpSecurity} para configurar la seguridad.
     * @return El {@link SecurityFilterChain} construido.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl(frontendUrl + loginPath)
                        .permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(coopHeaderFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}