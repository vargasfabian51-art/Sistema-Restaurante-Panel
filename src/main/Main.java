package main;

import vista.VentanaUsuarios;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaUsuarios().setVisible(true);
        });
    }
}
