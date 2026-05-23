package vista;

import dao.ClienteDAO;
import modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana gráfica para gestionar clientes.
 * Permite guardar, listar, actualizar y eliminar clientes.
 */
public class VentanaClientes extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtTelefono;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnVolver;

    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    private ClienteDAO clienteDAO;

    private final Color COLOR_OSCURO = new Color(45, 52, 54);
    private final Color COLOR_AZUL = new Color(9, 132, 227);
    private final Color COLOR_FONDO = new Color(245, 246, 250);

    public VentanaClientes() {
        clienteDAO = new ClienteDAO();

        setTitle("Gestión de Clientes - Sistema Restaurante");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);

        inicializarComponentes();
        cargarClientes();
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = new JLabel("GESTIÓN DE CLIENTES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(18, 10, 18, 10));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(COLOR_OSCURO);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBackground(COLOR_FONDO);
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(COLOR_FONDO);
        panelSuperior.add(panelTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Teléfono"}, 0);
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setRowHeight(24);
        tablaClientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaClientes.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(COLOR_FONDO);

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnVolver = new JButton("Volver");

        estilizarBoton(btnGuardar);
        estilizarBoton(btnActualizar);
        estilizarBoton(btnEliminar);
        estilizarBoton(btnLimpiar);
        estilizarBoton(btnVolver);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());

        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
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

    private void guardarCliente() {
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        Cliente cliente = new Cliente(nombre, telefono);
        boolean insertado = clienteDAO.insertarCliente(cliente);

        if (insertado) {
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente.");
            limpiarCampos();
            cargarClientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar cliente.");
        }
    }

    private void actualizarCliente() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar.");
            return;
        }

        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        int id = Integer.parseInt(txtId.getText());

        Cliente cliente = new Cliente(id, nombre, telefono);
        boolean actualizado = clienteDAO.actualizarCliente(cliente);

        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
            limpiarCampos();
            cargarClientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar cliente.");
        }
    }

    private void eliminarCliente() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.");
            return;
        }

        int id = Integer.parseInt(txtId.getText());

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar este cliente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            boolean eliminado = clienteDAO.eliminarCliente(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                limpiarCampos();
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar cliente.");
            }
        }
    }

    private void cargarClientes() {
        modeloTabla.setRowCount(0);

        List<Cliente> listaClientes = clienteDAO.listarClientes();

        for (Cliente cliente : listaClientes) {
            modeloTabla.addRow(new Object[]{
                    cliente.getIdCliente(),
                    cliente.getNombre(),
                    cliente.getTelefono()
            });
        }
    }

    private void cargarDatosDesdeTabla() {
        int fila = tablaClientes.getSelectedRow();

        if (fila != -1) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtTelefono.setText(modeloTabla.getValueAt(fila, 2).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        tablaClientes.clearSelection();
    }
}