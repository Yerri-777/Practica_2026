package modelo;

public class EstadoPedido {

    public static final EstadoPedido RECIBIDA
            = new EstadoPedido(0, "RECIBIDA");

    public static final EstadoPedido PENDIENTE
            = new EstadoPedido(1, "PENDIENTE");

    public static final EstadoPedido PREPARANDO
            = new EstadoPedido(2, "PREPARANDO");

    public static final EstadoPedido EN_HORNO
            = new EstadoPedido(3, "EN_HORNO");

    public static final EstadoPedido ENTREGADA
            = new EstadoPedido(4, "ENTREGADA");

    public static final EstadoPedido CANCELADA
            = new EstadoPedido(5, "CANCELADA");

    public static final EstadoPedido NO_ENTREGADO
            = new EstadoPedido(6, "NO_ENTREGADO");

    private int idEstado;
    private String nombreEstado;

    public EstadoPedido() {
    }

    public EstadoPedido(int idEstado, String nombreEstado) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    @Override
    public String toString() {
        return nombreEstado;
    }
}
