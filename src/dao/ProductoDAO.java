package dao;

import conexion.ConexionBD;
import modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de productos.
 * DAO significa Data Access Object.
 * Esta clase se encarga de comunicarse con la base de datos.
 */
public class ProductoDAO {

    /**
     * Inserta un nuevo producto en la base de datos.
     */
    public boolean insertarProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre, precio, categoria) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getCategoria());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los productos registrados.
     */
    public List<Producto> listarProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, precio, categoria FROM productos ORDER BY id_producto";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto();

                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCategoria(rs.getString("categoria"));

                listaProductos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return listaProductos;
    }

    /**
     * Actualiza los datos de un producto existente.
     */
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, categoria = ? WHERE id_producto = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getCategoria());
            ps.setInt(4, producto.getIdProducto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un producto usando su ID.
     */
    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un producto específico por su ID.
     */
    public Producto buscarProductoPorId(int idProducto) {
        String sql = "SELECT id_producto, nombre, precio, categoria FROM productos WHERE id_producto = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto producto = new Producto();

                    producto.setIdProducto(rs.getInt("id_producto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setCategoria(rs.getString("categoria"));

                    return producto;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }

        return null;
    }
}