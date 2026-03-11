package modelo;

public class DetallePedido {

    private int idDetalle;
    private int cantidad;

    private Pedido pedido;
    private Producto producto;

    public DetallePedido() {
    }

    public DetallePedido(int idDetalle, int cantidad,
            Pedido pedido, Producto producto) {
        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.pedido = pedido;
        this.producto = producto;
    }

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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}