package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Usuario;

/**
 * Ventana principal del sistema.
 * Desde esta pantalla se navega hacia los módulos principales.
 * También aplica permisos según el rol del usuario logueado.
 */
public class MenuPrincipal extends JFrame {

    private JButton btnUsuarios;
    private JButton btnProductos;
    private JButton btnClientes;
    private JButton btnPedidos;
    private JButton btnCerrarSesion;

    private final Color COLOR_OSCURO = new Color(45, 52, 54);
    private final Color COLOR_AZUL = new Color(9, 132, 227);
    private final Color COLOR_FONDO = new Color(245, 246, 250);

    private Usuario usuarioLogueado;

    /**
     * Constructor que recibe el usuario que inició sesión.
     */
    public MenuPrincipal(Usuario usuarioLogueado) {

        this.usuarioLogueado = usuarioLogueado;

        setTitle("Panel Administrativo - Restaurante");
        setSize(780, 590);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
        aplicarPermisosPorRol();
    }

    /**
     * Inicializa todos los componentes visuales.
     */
    private void inicializarComponentes() {

        // PANEL SUPERIOR CON LOGO
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(COLOR_OSCURO);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel lblLogo = cargarLogo();

        panelTitulo.add(lblLogo, BorderLayout.CENTER);

        add(panelTitulo, BorderLayout.NORTH);

        // PANEL CENTRAL CON BOTONES
        JPanel panelMenu = new JPanel(new GridLayout(5, 1, 15, 15));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(25, 160, 25, 160));
        panelMenu.setBackground(COLOR_FONDO);

        btnUsuarios = crearBotonMenu("Gestión de Usuarios");
        btnProductos = crearBotonMenu("Gestión de Productos");
        btnClientes = crearBotonMenu("Gestión de Clientes");
        btnPedidos = crearBotonMenu("Gestión de Pedidos");
        btnCerrarSesion = crearBotonMenu("Cerrar Sesión");

        panelMenu.add(btnUsuarios);
        panelMenu.add(btnProductos);
        panelMenu.add(btnClientes);
        panelMenu.add(btnPedidos);
        panelMenu.add(btnCerrarSesion);

        add(panelMenu, BorderLayout.CENTER);

        // EVENTOS DE BOTONES
        btnUsuarios.addActionListener(e -> abrirVentanaUsuarios());
        btnProductos.addActionListener(e -> abrirVentanaProductos());
        btnClientes.addActionListener(e -> abrirVentanaClientes());
        btnPedidos.addActionListener(e -> abrirVentanaPedidos());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        // PANEL INFERIOR
        JPanel panelFooter = new JPanel(new GridLayout(2, 1));
        panelFooter.setBackground(COLOR_OSCURO);
        panelFooter.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel lblUsuario = new JLabel(
                "Usuario: " + usuarioLogueado.getNombre() + " | Rol: " + usuarioLogueado.getRol(),
                SwingConstants.CENTER
        );
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel lblFooter = new JLabel("Java SE - Swing - PostgreSQL - JDBC", SwingConstants.CENTER);
        lblFooter.setForeground(Color.WHITE);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));

        panelFooter.add(lblUsuario);
        panelFooter.add(lblFooter);

        add(panelFooter, BorderLayout.SOUTH);
    }

    /**
     * Aplica permisos según el rol del usuario logueado.
     *
     * Administrador:
     * - Acceso completo.
     *
     * Cajero:
     * - Productos, Clientes y Pedidos.
     * - No puede gestionar Usuarios.
     *
     * Empleado:
     * - Clientes y Pedidos.
     * - No puede gestionar Usuarios ni Productos.
     */
    private void aplicarPermisosPorRol() {

        String rol = usuarioLogueado.getRol();

        if (rol.equalsIgnoreCase("Administrador")
                || rol.equalsIgnoreCase("Administrador General")) {

            btnUsuarios.setEnabled(true);
            btnProductos.setEnabled(true);
            btnClientes.setEnabled(true);
            btnPedidos.setEnabled(true);

        } else if (rol.equalsIgnoreCase("Cajero")) {

            btnUsuarios.setEnabled(false);
            btnProductos.setEnabled(true);
            btnClientes.setEnabled(true);
            btnPedidos.setEnabled(true);

        } else if (rol.equalsIgnoreCase("Empleado")) {

            btnUsuarios.setEnabled(false);
            btnProductos.setEnabled(false);
            btnClientes.setEnabled(true);
            btnPedidos.setEnabled(true);

        } else {

            btnUsuarios.setEnabled(false);
            btnProductos.setEnabled(false);
            btnClientes.setEnabled(false);
            btnPedidos.setEnabled(false);
        }
    }

    /**
     * Método para cargar el logo desde src/resources/logo.png.
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
     * Cierra la sesión actual y vuelve a la ventana de login.
     */
    private void cerrarSesion() {

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea cerrar sesión?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            JOptionPane.showMessageDialog(
                    this,
                    "Sesión cerrada correctamente."
            );

            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);

            dispose();
        }
    }
}