package main;

import vista.VentanaLogin;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaLogin().setVisible(true);
        });
    }
}