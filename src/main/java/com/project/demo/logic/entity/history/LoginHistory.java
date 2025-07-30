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

    /**
     * Obtiene el ID del registro de historial de inicio de sesión.
     *
     * @return El ID del registro de historial de inicio de sesión.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del registro de historial de inicio de sesión.
     *
     * @param id El nuevo ID del registro de historial de inicio de sesión.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario asociado a este registro de inicio de sesión.
     *
     * @return El usuario asociado a este registro de inicio de sesión.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario asociado a este registro de inicio de sesión.
     *
     * @param user El nuevo usuario asociado a este registro de inicio de sesión.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene la fecha y hora del inicio de sesión.
     *
     * @return La fecha y hora del inicio de sesión.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Establece la fecha y hora del inicio de sesión.
     *
     * @param date La nueva fecha y hora del inicio de sesión.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Obtiene la dirección IP desde la que se realizó el inicio de sesión.
     *
     * @return La dirección IP desde la que se realizó el inicio de sesión.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Establece la dirección IP desde la que se realizó el inicio de sesión.
     *
     * @param ip La nueva dirección IP desde la que se realizó el inicio de sesión.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Obtiene la información sobre el dispositivo utilizado para el inicio de sesión.
     *
     * @return La información sobre el dispositivo utilizado para el inicio de sesión.
     */
    public String getDevice() {
        return device;
    }

    /**
     * Establece la información sobre el dispositivo utilizado para el inicio de sesión.
     *
     * @param device La nueva información sobre el dispositivo utilizado para el inicio de sesión.
     */
    public void setDevice(String device) {
        this.device = device;
    }
}
