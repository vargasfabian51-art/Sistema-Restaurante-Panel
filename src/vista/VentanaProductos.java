package vista;

import dao.ProductoDAO;
import modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana gráfica para gestionar productos.
 * Desde esta pantalla se puede crear, listar, actualizar y eliminar productos.
 */
public class VentanaProductos extends JFrame {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCategoria;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnVolver;

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    private ProductoDAO productoDAO;

    public VentanaProductos() {
        productoDAO = new ProductoDAO();

        setTitle("Gestion de Productos - Sistema Restaurante");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
        cargarProductos();
    }

    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Categoria:"));
        txtCategoria = new JTextField();
        panelFormulario.add(txtCategoria);

        add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Precio", "Categoria"}, 0);
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnVolver = new JButton("Volver");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarProducto());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());

        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosDesdeTabla();
            }
        });
    }

    private void guardarProducto() {
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String categoria = txtCategoria.getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        double precio;

        try {
            precio = Double.parseDouble(precioTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un numero valido.");
            return;
        }

        if (precio <= 0) {
            JOptionPane.showMessageDialog(this, "El precio debe ser mayor que cero.");
            return;
        }

        Producto producto = new Producto(nombre, precio, categoria);
        boolean insertado = productoDAO.insertarProducto(producto);

        if (insertado) {
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");
            limpiarCampos();
            cargarProductos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar producto.");
        }
    }

    private void actualizarProducto() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para actualizar.");
            return;
        }

        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String categoria = txtCategoria.getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        double precio;

        try {
            precio = Double.parseDouble(precioTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un numero valido.");
            return;
        }

        if (precio <= 0) {
            JOptionPane.showMessageDialog(this, "El precio debe ser mayor que cero.");
            return;
        }

        int id = Integer.parseInt(txtId.getText());

        Producto producto = new Producto(id, nombre, precio, categoria);
        boolean actualizado = productoDAO.actualizarProducto(producto);

        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
            limpiarCampos();
            cargarProductos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar producto.");
        }
    }

    private void eliminarProducto() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.");
            return;
        }

        int id = Integer.parseInt(txtId.getText());

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "Desea eliminar este producto?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            boolean eliminado = productoDAO.eliminarProducto(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                limpiarCampos();
                cargarProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar producto.");
            }
        }
    }

    private void cargarProductos() {
        modeloTabla.setRowCount(0);

        List<Producto> listaProductos = productoDAO.listarProductos();

        for (Producto producto : listaProductos) {
            modeloTabla.addRow(new Object[]{
                    producto.getIdProducto(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getCategoria()
            });
        }
    }

    private void cargarDatosDesdeTabla() {
        int fila = tablaProductos.getSelectedRow();

        if (fila != -1) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtPrecio.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtCategoria.setText(modeloTabla.getValueAt(fila, 3).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCategoria.setText("");
        tablaProductos.clearSelection();
    }
}