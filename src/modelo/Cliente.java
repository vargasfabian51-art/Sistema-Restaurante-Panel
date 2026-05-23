package modelo;

/**
 * Clase modelo que representa un cliente.
 * Aqui se almacenan los datos del cliente.
 */
public class Cliente {

    // Atributos del cliente
    private int idCliente;
    private String nombre;
    private String telefono;

    /**
     * Constructor vacio
     */
    public Cliente() {
    }

    /**
     * Constructor con todos los atributos
     */
    public Cliente(int idCliente, String nombre, String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    /**
     * Constructor sin ID
     * Se usa para insertar nuevos clientes.
     */
    public Cliente(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // GETTERS Y SETTERS

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Metodo toString
     * Permite mostrar la informacion del cliente.
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}