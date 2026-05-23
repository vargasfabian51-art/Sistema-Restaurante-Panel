package modelo;

import java.util.Date;

/**
 * Clase modelo que representa un pedido.
 */
public class Pedido {

    private int idPedido;
    private Date fecha;
    private double total;
    private int idCliente;

    /**
     * Constructor vacio.
     */
    public Pedido() {
    }

    /**
     * Constructor con todos los atributos.
     */
    public Pedido(int idPedido, Date fecha, double total, int idCliente) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.total = total;
        this.idCliente = idCliente;
    }

    /**
     * Constructor sin ID.
     */
    public Pedido(Date fecha, double total, int idCliente) {
        this.fecha = fecha;
        this.total = total;
        this.idCliente = idCliente;
    }

    // GETTERS Y SETTERS

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Metodo toString.
     */
    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", fecha=" + fecha +
                ", total=" + total +
                ", idCliente=" + idCliente +
                '}';
    }
}