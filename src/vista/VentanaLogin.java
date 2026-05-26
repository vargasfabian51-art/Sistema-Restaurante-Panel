package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnSalir;

    private UsuarioDAO usuarioDAO;

    private final Color COLOR_OSCURO = new Color(45, 52, 54);
    private final Color COLOR_AZUL = new Color(9, 132, 227);
    private final Color COLOR_FONDO = new Color(245, 246, 250);

    public VentanaLogin() {
        usuarioDAO = new UsuarioDAO();

        setTitle("Inicio de Sesión - Sistema Restaurante");
        setSize(450, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(COLOR_OSCURO);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 15));
        panelFormulario.setBackground(COLOR_FONDO);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(35, 45, 25, 45));

        panelFormulario.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panelFormulario.add(txtContrasena);

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(COLOR_FONDO);

        btnIngresar = new JButton("Ingresar");
        btnSalir = new JButton("Salir");

        estilizarBoton(btnIngresar);
        estilizarBoton(btnSalir);

        panelBotones.add(btnIngresar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);

        btnIngresar.addActionListener(e -> iniciarSesion());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void estilizarBoton(JButton boton) {
        boton.setBackground(COLOR_AZUL);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar correo y contraseña.");
            return;
        }

        Usuario usuario = usuarioDAO.validarLogin(correo, contrasena);

        if (usuario != null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido " + usuario.getNombre() + "\nRol: " + usuario.getRol()
            );

            MenuPrincipal menu = new MenuPrincipal(usuario);
            menu.setVisible(true);

            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos.");
        }
    }
}