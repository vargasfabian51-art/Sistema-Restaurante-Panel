package vista;

import dao.ClienteDAO;
import dao.DetallePedidoDAO;
import dao.PedidoDAO;
import dao.ProductoDAO;
import modelo.Cliente;
import modelo.DetallePedido;
import modelo.Pedido;
import modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ventana para registrar pedidos.
 * Permite seleccionar un cliente, agregar productos al pedido
 * y guardar el pedido con su respectivo detalle.
 */
public class VentanaPedidos extends JFrame {

    private JComboBox<Cliente> comboClientes;
    private JComboBox<Producto> comboProductos;
    private JTextField txtCantidad;
    private JTextField txtTotal;

    private JButton btnAgregarProducto;
    private JButton btnGuardarPedido;
    private JButton btnLimpiar;
    private JButton btnVolver;

    private JTable tablaDetalle;
    private DefaultTableModel modeloTabla;

    private ClienteDAO clienteDAO;
    private ProductoDAO productoDAO;
    private PedidoDAO pedidoDAO;
    private DetallePedidoDAO detallePedidoDAO;

    private List<Producto> productosPedido;
    private List<Integer> cantidadesPedido;

    private double totalPedido;

    public VentanaPedidos() {
        clienteDAO = new ClienteDAO();
        productoDAO = new ProductoDAO();
        pedidoDAO = new PedidoDAO();
        detallePedidoDAO = new DetallePedidoDAO();

        productosPedido = new ArrayList<>();
        cantidadesPedido = new ArrayList<>();

        totalPedido = 0;

        setTitle("Gestion de Pedidos - Sistema Restaurante");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarComponentes();
        cargarClientes();
        cargarProductos();
    }

    private void inicializarComponentes() {
        JPanel panelSuperior = new JPanel(new GridLayout(4, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));

        panelSuperior.add(new JLabel("Cliente:"));
        comboClientes = new JComboBox<>();
        panelSuperior.add(comboClientes);

        panelSuperior.add(new JLabel("Producto:"));
        comboProductos = new JComboBox<>();
        panelSuperior.add(comboProductos);

        panelSuperior.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelSuperior.add(txtCantidad);

        panelSuperior.add(new JLabel("Total:"));
        txtTotal = new JTextField("0.0");
        txtTotal.setEditable(false);
        panelSuperior.add(txtTotal);

        add(panelSuperior, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{"Producto", "Precio", "Cantidad", "Subtotal"}, 0
        );

        tablaDetalle = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaDetalle);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());

        btnAgregarProducto = new JButton("Agregar Producto");
        btnGuardarPedido = new JButton("Guardar Pedido");
        btnLimpiar = new JButton("Limpiar");
        btnVolver = new JButton("Volver");

        panelBotones.add(btnAgregarProducto);
        panelBotones.add(btnGuardarPedido);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        btnAgregarProducto.addActionListener(e -> agregarProductoAlPedido());
        btnGuardarPedido.addActionListener(e -> guardarPedido());
        btnLimpiar.addActionListener(e -> limpiarPedido());
        btnVolver.addActionListener(e -> dispose());
    }

    /**
     * Carga los clientes en el combo.
     */
    private void cargarClientes() {
        comboClientes.removeAllItems();

        List<Cliente> clientes = clienteDAO.listarClientes();

        for (Cliente cliente : clientes) {
            comboClientes.addItem(cliente);
        }
    }

    /**
     * Carga los productos en el combo.
     */
    private void cargarProductos() {
        comboProductos.removeAllItems();

        List<Producto> productos = productoDAO.listarProductos();

        for (Producto producto : productos) {
            comboProductos.addItem(producto);
        }
    }

    /**
     * Agrega un producto temporalmente al pedido.
     * Todavia no guarda en la base de datos.
     */
    private void agregarProductoAlPedido() {
        Producto productoSeleccionado = (Producto) comboProductos.getSelectedItem();

        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un producto.");
            return;
        }

        String cantidadTexto = txtCantidad.getText().trim();

        if (cantidadTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la cantidad.");
            return;
        }

        int cantidad;

        try {
            cantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un numero entero.");
            return;
        }

        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.");
            return;
        }

        double subtotal = productoSeleccionado.getPrecio() * cantidad;

        productosPedido.add(productoSeleccionado);
        cantidadesPedido.add(cantidad);

        modeloTabla.addRow(new Object[]{
                productoSeleccionado.getNombre(),
                productoSeleccionado.getPrecio(),
                cantidad,
                subtotal
        });

        totalPedido += subtotal;
        txtTotal.setText(String.valueOf(totalPedido));

        txtCantidad.setText("");
    }

    /**
     * Guarda el pedido en la tabla pedidos y luego guarda
     * cada producto agregado en la tabla detalle_pedido.
     */
    private void guardarPedido() {
        Cliente clienteSeleccionado = (Cliente) comboClientes.getSelectedItem();

        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.");
            return;
        }

        if (productosPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos un producto al pedido.");
            return;
        }

        Pedido pedido = new Pedido(
                new Date(),
                totalPedido,
                clienteSeleccionado.getIdCliente()
        );

        boolean pedidoInsertado = pedidoDAO.insertarPedido(pedido);

        if (!pedidoInsertado) {
            JOptionPane.showMessageDialog(this, "Error al guardar el pedido.");
            return;
        }

        /*
         * IMPORTANTE:
         * Como el pedido se acaba de insertar, necesitamos saber el último ID.
         * Por simplicidad académica, consultaremos el último pedido creado.
         */
        int idPedidoGenerado = obtenerUltimoIdPedido();

        if (idPedidoGenerado == -1) {
            JOptionPane.showMessageDialog(this, "No se pudo obtener el ID del pedido.");
            return;
        }

        boolean detallesGuardados = true;

        for (int i = 0; i < productosPedido.size(); i++) {
            Producto producto = productosPedido.get(i);
            int cantidad = cantidadesPedido.get(i);

            DetallePedido detalle = new DetallePedido(
                    cantidad,
                    idPedidoGenerado,
                    producto.getIdProducto()
            );

            boolean detalleInsertado = detallePedidoDAO.insertarDetallePedido(detalle);

            if (!detalleInsertado) {
                detallesGuardados = false;
            }
        }

        if (detallesGuardados) {
            JOptionPane.showMessageDialog(this, "Pedido guardado correctamente.");
            limpiarPedido();
        } else {
            JOptionPane.showMessageDialog(this, "Pedido guardado, pero hubo error en algun detalle.");
        }
    }

    /**
     * Metodo auxiliar para obtener el ultimo ID de pedido.
     * Se usa para relacionar el pedido con su detalle.
     */
    private int obtenerUltimoIdPedido() {
        List<Pedido> pedidos = pedidoDAO.listarPedidos();

        if (pedidos.isEmpty()) {
            return -1;
        }

        Pedido ultimoPedido = pedidos.get(pedidos.size() - 1);
        return ultimoPedido.getIdPedido();
    }

    /**
     * Limpia todos los datos del pedido actual.
     */
    private void limpiarPedido() {
        productosPedido.clear();
        cantidadesPedido.clear();
        modeloTabla.setRowCount(0);
        txtCantidad.setText("");
        txtTotal.setText("0.0");
        totalPedido = 0;
    }
}