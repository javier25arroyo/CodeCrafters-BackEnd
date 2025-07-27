package com.project.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot. Esta clase inicia la aplicación y configura el
 * auto-escaneo de componentes.
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Método principal que arranca la aplicación Spring Boot.
     *
     * @param args Argumentos de línea de comandos pasados a la aplicación.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
