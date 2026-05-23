package main;

import vista.MenuPrincipal;

/**
 * Clase principal del proyecto.
 * Inicia el menu principal del sistema.
 */
public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}