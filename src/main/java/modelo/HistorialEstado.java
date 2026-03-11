package modelo;

import java.time.LocalDateTime;

public class HistorialEstado {

    private int idHistorial;
    private LocalDateTime fechaCambio;

    private Pedido pedido;
    private EstadoPedido estado;

    public HistorialEstado() {}

    public HistorialEstado(int idHistorial, LocalDateTime fechaCambio,
                           Pedido pedido, EstadoPedido estado) {
        this.idHistorial = idHistorial;
        this.fechaCambio = fechaCambio;
        this.pedido = pedido;
        this.estado = estado;
    }

    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public LocalDateTime getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(LocalDateTime fechaCambio) { this.fechaCambio = fechaCambio; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
}