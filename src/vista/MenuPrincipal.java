package vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private JButton btnUsuarios;
    private JButton btnProductos;
    private JButton btnClientes;
    private JButton btnPedidos;
    private JButton btnSalir;

    public MenuPrincipal() {
        setTitle("Panel Administrativo - Sistema Restaurante");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JLabel titulo = new JLabel("SISTEMA DE GESTION DE RESTAURANTE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));

        btnUsuarios = new JButton("Gestion de Usuarios");
        btnProductos = new JButton("Gestion de Productos");
        btnClientes = new JButton("Gestion de Clientes");
        btnPedidos = new JButton("Gestion de Pedidos");
        btnSalir = new JButton("Salir");

        panelBotones.add(btnUsuarios);
        panelBotones.add(btnProductos);
        panelBotones.add(btnClientes);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        btnUsuarios.addActionListener(e -> abrirVentanaUsuarios());
        btnProductos.addActionListener(e -> abrirVentanaProductos());
        btnClientes.addActionListener(e -> abrirVentanaClientes());
        btnSalir.addActionListener(e -> salirDelSistema());
        btnPedidos.addActionListener(e -> abrirVentanaPedidos());
    }

    private void abrirVentanaUsuarios() {
        VentanaUsuarios ventanaUsuarios = new VentanaUsuarios();
        ventanaUsuarios.setVisible(true);
    }

    private void salirDelSistema() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    private void abrirVentanaProductos() {
    VentanaProductos ventanaProductos = new VentanaProductos();
    ventanaProductos.setVisible(true);
}
    private void abrirVentanaClientes() {
    VentanaClientes ventanaClientes = new VentanaClientes();
    ventanaClientes.setVisible(true);
}
    private void abrirVentanaPedidos() {
    VentanaPedidos ventanaPedidos = new VentanaPedidos();
    ventanaPedidos.setVisible(true);
}
}