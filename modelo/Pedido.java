package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int idPedido;
    private int tiempoLimite;
    private LocalDateTime tiempoCreacion;
    private LocalDateTime tiempoFinalizacion;

    private EstadoPedido estadoActual;
    private Partida partida;

    private List<DetallePedido> detalles;
    private List<HistorialEstado> historialEstados;

    public Pedido() {
        this.detalles = new ArrayList<>();
        this.historialEstados = new ArrayList<>();
    }

    public Pedido(int idPedido, int tiempoLimite,
            LocalDateTime tiempoCreacion,
            LocalDateTime tiempoFinalizacion,
            EstadoPedido estadoActual,
            Partida partida) {
        this.idPedido = idPedido;
        this.tiempoLimite = tiempoLimite;
        this.tiempoCreacion = tiempoCreacion;
        this.tiempoFinalizacion = tiempoFinalizacion;
        this.estadoActual = estadoActual;
        this.partida = partida;
        this.detalles = new ArrayList<>();
        this.historialEstados = new ArrayList<>();
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getTiempoLimite() {
        return tiempoLimite;
    }

    public void setTiempoLimite(int tiempoLimite) {
        this.tiempoLimite = tiempoLimite;
    }

    public LocalDateTime getTiempoCreacion() {
        return tiempoCreacion;
    }

    public void setTiempoCreacion(LocalDateTime tiempoCreacion) {
        this.tiempoCreacion = tiempoCreacion;
    }

    public LocalDateTime getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }

    public void setTiempoFinalizacion(LocalDateTime tiempoFinalizacion) {
        this.tiempoFinalizacion = tiempoFinalizacion;
    }

    public EstadoPedido getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(EstadoPedido estadoActual) {
        this.estadoActual = estadoActual;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public List<HistorialEstado> getHistorialEstados() {
        return historialEstados;
    }

    public void setHistorialEstados(List<HistorialEstado> historialEstados) {
        this.historialEstados = historialEstados;
    }
}