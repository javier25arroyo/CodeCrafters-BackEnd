package com.project.demo.logic.entity.game;

import jakarta.persistence.*;

/**
 * Representa un componente individual de un juego, como una pregunta, una imagen o un elemento interactivo.
 * Esta entidad mapea la tabla 'game_components' en la base de datos.
 */
@Entity
@Table(name = "game_components")
public class GameComponent {
    /**
     * Identificador único del componente del juego.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El juego al que pertenece este componente.
     */
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    /**
     * El tipo de componente (ej. "pregunta", "imagen", "video").
     */
    @Column(length = 50)
    private String type;

    /**
     * El contenido del componente, que puede ser texto, una URL, etc.
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * Constructor por defecto.
     */
    public GameComponent() {
    }

    /**
     * Constructor para crear una instancia de GameComponent con todos los parámetros.
     *
     * @param id      Identificador único.
     * @param game    Juego asociado.
     * @param type    Tipo de componente.
     * @param content Contenido del componente.
     */
    public GameComponent(Integer id, Game game, String type, String content) {
        this.id = id;
        this.game = game;
        this.type = type;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
