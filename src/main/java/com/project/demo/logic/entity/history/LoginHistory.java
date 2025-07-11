package com.project.demo.logic.entity.history;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Representa un registro del historial de inicio de sesión de un usuario.
 * Esta entidad mapea la tabla 'login_histories' en la base de datos.
 */
@Entity
@Table(name = "login_histories")
public class LoginHistory {
    /**
     * Identificador único del registro de historial de inicio de sesión.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El usuario asociado a este registro de inicio de sesión.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * La fecha y hora del inicio de sesión.
     */
    private LocalDateTime date;

    /**
     * La dirección IP desde la que se realizó el inicio de sesión.
     */
    @Column(length = 45)
    private String ip;

    /**
     * Información sobre el dispositivo utilizado para el inicio de sesión.
     */
    @Column(length = 100)
    private String device;

    /**
     * Constructor por defecto.
     */
    public LoginHistory() {
    }

    /**
     * Constructor para crear una instancia de LoginHistory con todos los parámetros.
     *
     * @param id     Identificador único.
     * @param user   Usuario asociado.
     * @param date   Fecha y hora del inicio de sesión.
     * @param ip     Dirección IP.
     * @param device Información del dispositivo.
     */
    public LoginHistory(Integer id, User user, LocalDateTime date, String ip, String device) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.ip = ip;
        this.device = device;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
