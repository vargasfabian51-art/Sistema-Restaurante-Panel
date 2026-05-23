package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema.
 * Desde esta pantalla se navega hacia los módulos principales.
 */
public class MenuPrincipal extends JFrame {

    private JButton btnUsuarios;
    private JButton btnProductos;
    private JButton btnClientes;
    private JButton btnPedidos;
    private JButton btnSalir;

    private final Color COLOR_OSCURO = new Color(45, 52, 54);
    private final Color COLOR_AZUL = new Color(9, 132, 227);
    private final Color COLOR_FONDO = new Color(245, 246, 250);

    public MenuPrincipal() {

        setTitle("Panel Administrativo - Restaurante");
        setSize(780, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
    }

    /**
     * Inicializa todos los componentes visuales.
     */
    private void inicializarComponentes() {

        // PANEL SUPERIOR
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(COLOR_OSCURO);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel lblLogo = cargarLogo();

        panelTitulo.add(lblLogo, BorderLayout.CENTER);

        add(panelTitulo, BorderLayout.NORTH);

        // PANEL CENTRAL
        JPanel panelMenu = new JPanel(new GridLayout(5, 1, 15, 15));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(35, 160, 35, 160));
        panelMenu.setBackground(COLOR_FONDO);

        btnUsuarios = crearBotonMenu("Gestión de Usuarios");
        btnProductos = crearBotonMenu("Gestión de Productos");
        btnClientes = crearBotonMenu("Gestión de Clientes");
        btnPedidos = crearBotonMenu("Gestión de Pedidos");
        btnSalir = crearBotonMenu("Salir del Sistema");

        panelMenu.add(btnUsuarios);
        panelMenu.add(btnProductos);
        panelMenu.add(btnClientes);
        panelMenu.add(btnPedidos);
        panelMenu.add(btnSalir);

        add(panelMenu, BorderLayout.CENTER);

        // EVENTOS DE BOTONES
        btnUsuarios.addActionListener(e -> abrirVentanaUsuarios());

        btnProductos.addActionListener(e -> abrirVentanaProductos());

        btnClientes.addActionListener(e -> abrirVentanaClientes());

        btnPedidos.addActionListener(e -> abrirVentanaPedidos());

        btnSalir.addActionListener(e -> salirDelSistema());

        // PANEL INFERIOR
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(COLOR_OSCURO);
        panelFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblFooter = new JLabel("Java SE - Swing - PostgreSQL - JDBC");
        lblFooter.setForeground(Color.WHITE);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));

        panelFooter.add(lblFooter);

        add(panelFooter, BorderLayout.SOUTH);
    }

    /**
     * Método para cargar el logo.
     */
    private JLabel cargarLogo() {

        JLabel lblLogo;

        try {

            ImageIcon iconoLogo = new ImageIcon(
                    getClass().getResource("/resources/logo.png")
            );

            Image imagenLogo = iconoLogo.getImage().getScaledInstance(
                    430,
                    120,
                    Image.SCALE_SMOOTH
            );

            lblLogo = new JLabel(new ImageIcon(imagenLogo));
            lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        } catch (Exception e) {

            lblLogo = new JLabel("RESTO ADMIN", SwingConstants.CENTER);
            lblLogo.setForeground(Color.WHITE);
            lblLogo.setFont(new Font("Arial", Font.BOLD, 30));
        }

        return lblLogo;
    }

    /**
     * Crea botones con el mismo diseño.
     */
    private JButton crearBotonMenu(String texto) {

        JButton boton = new JButton(texto);

        boton.setFont(new Font("Arial", Font.BOLD, 15));
        boton.setFocusPainted(false);
        boton.setBackground(COLOR_AZUL);
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    /**
     * Abre ventana de usuarios.
     */
    private void abrirVentanaUsuarios() {

        VentanaUsuarios ventanaUsuarios = new VentanaUsuarios();
        ventanaUsuarios.setVisible(true);
    }

    /**
     * Abre ventana de productos.
     */
    private void abrirVentanaProductos() {

        VentanaProductos ventanaProductos = new VentanaProductos();
        ventanaProductos.setVisible(true);
    }

    /**
     * Abre ventana de clientes.
     */
    private void abrirVentanaClientes() {

        VentanaClientes ventanaClientes = new VentanaClientes();
        ventanaClientes.setVisible(true);
    }

    /**
     * Abre ventana de pedidos.
     */
    private void abrirVentanaPedidos() {

        VentanaPedidos ventanaPedidos = new VentanaPedidos();
        ventanaPedidos.setVisible(true);
    }

    /**
     * Cierra el sistema.
     */
    private void salirDelSistema() {

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            JOptionPane.showMessageDialog(
                    this,
                    "Gracias por usar el sistema."
            );

            System.exit(0);
        }
    }
}