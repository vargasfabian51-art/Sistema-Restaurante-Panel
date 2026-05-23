package dao;

import conexion.ConexionBD;
import modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para manejar las operaciones CRUD de pedidos.
 */
public class PedidoDAO {

    /**
     * Inserta un nuevo pedido.
     */
    public boolean insertarPedido(Pedido pedido) {

        String sql = "INSERT INTO pedidos (fecha, total, id_cliente) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            // Convertimos java.util.Date a java.sql.Date
            java.sql.Date fechaSQL = new java.sql.Date(pedido.getFecha().getTime());

            ps.setDate(1, fechaSQL);
            ps.setDouble(2, pedido.getTotal());
            ps.setInt(3, pedido.getIdCliente());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los pedidos registrados.
     */
    public List<Pedido> listarPedidos() {

        List<Pedido> listaPedidos = new ArrayList<>();

        String sql = "SELECT id_pedido, fecha, total, id_cliente FROM pedidos ORDER BY id_pedido";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pedido pedido = new Pedido();

                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setFecha(rs.getDate("fecha"));
                pedido.setTotal(rs.getDouble("total"));
                pedido.setIdCliente(rs.getInt("id_cliente"));

                listaPedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }

        return listaPedidos;
    }

    /**
     * Actualiza un pedido existente.
     */
    public boolean actualizarPedido(Pedido pedido) {

        String sql = "UPDATE pedidos SET fecha = ?, total = ?, id_cliente = ? WHERE id_pedido = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            java.sql.Date fechaSQL = new java.sql.Date(pedido.getFecha().getTime());

            ps.setDate(1, fechaSQL);
            ps.setDouble(2, pedido.getTotal());
            ps.setInt(3, pedido.getIdCliente());
            ps.setInt(4, pedido.getIdPedido());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un pedido por ID.
     */
    public boolean eliminarPedido(int idPedido) {

        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un pedido por ID.
     */
    public Pedido buscarPedidoPorId(int idPedido) {

        String sql = "SELECT id_pedido, fecha, total, id_cliente FROM pedidos WHERE id_pedido = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Pedido pedido = new Pedido();

                    pedido.setIdPedido(rs.getInt("id_pedido"));
                    pedido.setFecha(rs.getDate("fecha"));
                    pedido.setTotal(rs.getDouble("total"));
                    pedido.setIdCliente(rs.getInt("id_cliente"));

                    return pedido;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar pedido: " + e.getMessage());
        }

        return null;
    }
}