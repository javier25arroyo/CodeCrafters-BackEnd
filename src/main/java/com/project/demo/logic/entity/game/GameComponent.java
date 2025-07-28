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

    /**
     * Obtiene el ID del componente del juego.
     *
     * @return El ID del componente del juego.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del componente del juego.
     *
     * @param id El nuevo ID del componente del juego.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el juego al que pertenece este componente.
     *
     * @return El juego al que pertenece este componente.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Establece el juego al que pertenece este componente.
     *
     * @param game El nuevo juego al que pertenece este componente.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Obtiene el tipo de componente.
     *
     * @return El tipo de componente.
     */
    public String getType() {
        return type;
    }

    /**
     * Establece el tipo de componente.
     *
     * @param type El nuevo tipo de componente.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtiene el contenido del componente.
     *
     * @return El contenido del componente.
     */
    public String getContent() {
        return content;
    }

    /**
     * Establece el contenido del componente.
     *
     * @param content El nuevo contenido del componente.
     */
    public void setContent(String content) {
        this.content = content;
    }
}
