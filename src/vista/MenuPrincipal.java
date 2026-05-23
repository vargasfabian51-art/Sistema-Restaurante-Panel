package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema.
 * Desde aqui se navega hacia los modulos del panel administrativo.
 */
public class MenuPrincipal extends JFrame {

    private JButton btnUsuarios;
    private JButton btnProductos;
    private JButton btnClientes;
    private JButton btnPedidos;
    private JButton btnSalir;

    public MenuPrincipal() {
        setTitle("Panel Administrativo - Restaurante");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        // Panel superior con titulo
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new GridLayout(2, 1));
        panelTitulo.setBackground(new Color(45, 52, 54));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JLabel lblTitulo = new JLabel("SISTEMA DE GESTION DE RESTAURANTE", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel lblSubtitulo = new JLabel("Panel Administrativo", SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(223, 230, 233));
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));

        panelTitulo.add(lblTitulo);
        panelTitulo.add(lblSubtitulo);

        add(panelTitulo, BorderLayout.NORTH);

        // Panel central con botones del menu
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1, 15, 15));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(35, 130, 35, 130));
        panelMenu.setBackground(new Color(245, 246, 250));

        btnUsuarios = crearBotonMenu(" Usuarios");
        btnProductos = crearBotonMenu(" Productos");
        btnClientes = crearBotonMenu(" Clientes");
        btnPedidos = crearBotonMenu(" Pedidos");
        btnSalir = crearBotonMenu("Salir");

        panelMenu.add(btnUsuarios);
        panelMenu.add(btnProductos);
        panelMenu.add(btnClientes);
        panelMenu.add(btnPedidos);
        panelMenu.add(btnSalir);

        add(panelMenu, BorderLayout.CENTER);

        // Eventos de los botones
        btnUsuarios.addActionListener(e -> abrirVentanaUsuarios());
        btnProductos.addActionListener(e -> abrirVentanaProductos());
        btnClientes.addActionListener(e -> abrirVentanaClientes());
        btnPedidos.addActionListener(e -> abrirVentanaPedidos());
        btnSalir.addActionListener(e -> salirDelSistema());

        // Panel inferior
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(new Color(45, 52, 54));
        panelFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblFooter = new JLabel("Java SE - Swing - PostgreSQL - JDBC");
        lblFooter.setForeground(Color.WHITE);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));

        panelFooter.add(lblFooter);

        add(panelFooter, BorderLayout.SOUTH);
    }

    /**
     * Metodo reutilizable para crear botones con el mismo estilo.
     */
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 15));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(9, 132, 227));
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void abrirVentanaUsuarios() {
        VentanaUsuarios ventanaUsuarios = new VentanaUsuarios();
        ventanaUsuarios.setVisible(true);
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

    private void salirDelSistema() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Gracias por usar el sistema.");
            System.exit(0);
        }
    }
}