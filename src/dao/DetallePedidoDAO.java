package dao;

import conexion.ConexionBD;
import modelo.DetallePedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para manejar las operaciones CRUD
 * de la tabla detalle_pedido.
 */
public class DetallePedidoDAO {

    /**
     * Inserta un nuevo detalle de pedido.
     */
    public boolean insertarDetallePedido(DetallePedido detallePedido) {

        String sql = "INSERT INTO detalle_pedido (cantidad, id_pedido, id_producto) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, detallePedido.getCantidad());
            ps.setInt(2, detallePedido.getIdPedido());
            ps.setInt(3, detallePedido.getIdProducto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar detalle del pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los detalles registrados.
     */
    public List<DetallePedido> listarDetallesPedido() {

        List<DetallePedido> listaDetalles = new ArrayList<>();

        String sql = "SELECT id_detalle, cantidad, id_pedido, id_producto FROM detalle_pedido ORDER BY id_detalle";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                DetallePedido detalle = new DetallePedido();

                detalle.setIdDetalle(rs.getInt("id_detalle"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setIdPedido(rs.getInt("id_pedido"));
                detalle.setIdProducto(rs.getInt("id_producto"));

                listaDetalles.add(detalle);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar detalles del pedido: " + e.getMessage());
        }

        return listaDetalles;
    }

    /**
     * Actualiza un detalle existente.
     */
    public boolean actualizarDetallePedido(DetallePedido detallePedido) {

        String sql = "UPDATE detalle_pedido SET cantidad = ?, id_pedido = ?, id_producto = ? WHERE id_detalle = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, detallePedido.getCantidad());
            ps.setInt(2, detallePedido.getIdPedido());
            ps.setInt(3, detallePedido.getIdProducto());
            ps.setInt(4, detallePedido.getIdDetalle());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar detalle del pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un detalle por ID.
     */
    public boolean eliminarDetallePedido(int idDetalle) {

        String sql = "DELETE FROM detalle_pedido WHERE id_detalle = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idDetalle);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar detalle del pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un detalle específico por ID.
     */
    public DetallePedido buscarDetallePedidoPorId(int idDetalle) {

        String sql = "SELECT id_detalle, cantidad, id_pedido, id_producto FROM detalle_pedido WHERE id_detalle = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idDetalle);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    DetallePedido detalle = new DetallePedido();

                    detalle.setIdDetalle(rs.getInt("id_detalle"));
                    detalle.setCantidad(rs.getInt("cantidad"));
                    detalle.setIdPedido(rs.getInt("id_pedido"));
                    detalle.setIdProducto(rs.getInt("id_producto"));

                    return detalle;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar detalle del pedido: " + e.getMessage());
        }

        return null;
    }
}