package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaUsuarios extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtContrasena;
    private JTextField txtRol;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnCargar;

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    private UsuarioDAO usuarioDAO;

    public VentanaUsuarios() {
        usuarioDAO = new UsuarioDAO();

        setTitle("Gestion de Usuarios - Sistema Restaurante");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
        cargarUsuarios();
    }

    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
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

        panelFormulario.add(new JLabel("Contrasena:"));
        txtContrasena = new JTextField();
        panelFormulario.add(txtContrasena);

        panelFormulario.add(new JLabel("Rol:"));
        txtRol = new JTextField();
        panelFormulario.add(txtRol);

        add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Correo", "Contrasena", "Rol"}, 0);
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnCargar = new JButton("Cargar Usuarios");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCargar);

        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCargar.addActionListener(e -> cargarUsuarios());

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosDesdeTabla();
            }
        });
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
                "Esta seguro de eliminar este usuario?",
                "Confirmar eliminacion",
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
