package modelo;

/**
 * Clase modelo que representa el detalle de un pedido.
 * Cada detalle indica qué producto se agregó al pedido y en qué cantidad.
 */
public class DetallePedido {

    private int idDetalle;
    private int cantidad;
    private int idPedido;
    private int idProducto;

    /**
     * Constructor vacío.
     */
    public DetallePedido() {
    }

    /**
     * Constructor con todos los atributos.
     */
    public DetallePedido(int idDetalle, int cantidad, int idPedido, int idProducto) {
        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
    }

    /**
     * Constructor sin ID.
     * Se usa cuando vamos a insertar un nuevo detalle en la base de datos.
     */
    public DetallePedido(int cantidad, int idPedido, int idProducto) {
        this.cantidad = cantidad;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
    }

    // GETTERS Y SETTERS

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Método toString para imprimir el objeto en consola.
     */
    @Override
    public String toString() {
        return "DetallePedido{" +
                "idDetalle=" + idDetalle +
                ", cantidad=" + cantidad +
                ", idPedido=" + idPedido +
                ", idProducto=" + idProducto +
                '}';
    }
}