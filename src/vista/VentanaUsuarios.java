package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaUsuarios extends JFrame {

    private JTextField txtId, txtNombre, txtCorreo, txtContrasena, txtRol;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar, btnCargar, btnVolver;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private UsuarioDAO usuarioDAO;

    private final Color COLOR_OSCURO = new Color(45, 52, 54);
    private final Color COLOR_AZUL = new Color(9, 132, 227);
    private final Color COLOR_FONDO = new Color(245, 246, 250);

    public VentanaUsuarios() {
        usuarioDAO = new UsuarioDAO();

        setTitle("Gestión de Usuarios - Sistema Restaurante");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);

        inicializarComponentes();
        cargarUsuarios();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("GESTIÓN DE USUARIOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(18, 10, 18, 10));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(COLOR_OSCURO);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBackground(COLOR_FONDO);
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));

        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtContrasena = new JTextField();
        panelFormulario.add(txtContrasena);

        panelFormulario.add(new JLabel("Rol:"));
        txtRol = new JTextField();
        panelFormulario.add(txtRol);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(COLOR_FONDO);
        panelSuperior.add(panelTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Correo", "Contraseña", "Rol"}, 0
        );

        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setRowHeight(24);
        tablaUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaUsuarios.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(COLOR_FONDO);

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnCargar = new JButton("Cargar Usuarios");
        btnVolver = new JButton("Volver");

        estilizarBoton(btnGuardar);
        estilizarBoton(btnActualizar);
        estilizarBoton(btnEliminar);
        estilizarBoton(btnLimpiar);
        estilizarBoton(btnCargar);
        estilizarBoton(btnVolver);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCargar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCargar.addActionListener(e -> cargarUsuarios());
        btnVolver.addActionListener(e -> dispose());

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosDesdeTabla();
            }
        });
    }

    private void estilizarBoton(JButton boton) {
        boton.setBackground(COLOR_AZUL);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void guardarUsuario() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        String rol = txtRol.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        Usuario usuario = new Usuario(nombre, correo, contrasena, rol);
        boolean insertado = usuarioDAO.insertarUsuario(usuario);

        if (insertado) {
            JOptionPane.showMessageDialog(this, "Usuario guardado correctamente.");
            limpiarCampos();
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar usuario.");
        }
    }

    private void actualizarUsuario() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para actualizar.");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        String rol = txtRol.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        Usuario usuario = new Usuario(id, nombre, correo, contrasena, rol);
        boolean actualizado = usuarioDAO.actualizarUsuario(usuario);

        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
            limpiarCampos();
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar usuario.");
        }
    }

    private void eliminarUsuario() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = usuarioDAO.eliminarUsuario(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
                limpiarCampos();
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario.");
            }
        }
    }

    private void cargarUsuarios() {
        modeloTabla.setRowCount(0);
        List<Usuario> listaUsuarios = usuarioDAO.listarUsuarios();

        for (Usuario usuario : listaUsuarios) {
            modeloTabla.addRow(new Object[]{
                    usuario.getIdUsuario(),
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.getContrasena(),
                    usuario.getRol()
            });
        }
    }

    private void cargarDatosDesdeTabla() {
        int fila = tablaUsuarios.getSelectedRow();

        if (fila != -1) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtCorreo.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtContrasena.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtRol.setText(modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtContrasena.setText("");
        txtRol.setText("");
        tablaUsuarios.clearSelection();
    }
}