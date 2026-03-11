package modelo;

import java.time.LocalDateTime;

public class Pedido {

    private int idPedido;
    private int tiempoLimite;
    private LocalDateTime tiempoCreacion;
    private LocalDateTime tiempoFinalizacion;

    private EstadoPedido estadoActual;
    private Partida partida;
    private Pizza pizza; 

    
    private DetallePedido[] detalles;
    private int cantidadDetalles;
    
    private HistorialEstado[] historialEstados;
    private int cantidadHistorial;

    public Pedido() {
        this.detalles = new DetallePedido[10];
        this.cantidadDetalles = 0;
        this.historialEstados = new HistorialEstado[10];
        this.cantidadHistorial = 0;
    }

    public Pedido(int idPedido, int tiempoLimite,
            LocalDateTime tiempoCreacion,
            LocalDateTime tiempoFinalizacion,
            EstadoPedido estadoActual,
            Partida partida,
            Pizza pizza) { 
        this.idPedido = idPedido;
        this.tiempoLimite = tiempoLimite;
        this.tiempoCreacion = tiempoCreacion;
        this.tiempoFinalizacion = tiempoFinalizacion;
        this.estadoActual = estadoActual;
        this.partida = partida;
        this.pizza = pizza;
        this.detalles = new DetallePedido[10];
        this.cantidadDetalles = 0;
        this.historialEstados = new HistorialEstado[10];
        this.cantidadHistorial = 0;
    }

    // Métodos para agregar elementos manualmente
    public void agregarDetalle(DetallePedido d) {
        if (cantidadDetalles < detalles.length) {
            detalles[cantidadDetalles++] = d;
        }
    }

    public void agregarHistorial(HistorialEstado h) {
        if (cantidadHistorial < historialEstados.length) {
            historialEstados[cantidadHistorial++] = h;
        }
    }
    
    
    public boolean estaFinalizado() {

        return estadoActual == EstadoPedido.ENTREGADA
                || estadoActual == EstadoPedido.CANCELADA
                || estadoActual == EstadoPedido.NO_ENTREGADO;

    }
    
    
    public void cambiarEstado(EstadoPedido nuevoEstado) {

        this.estadoActual = nuevoEstado;

        HistorialEstado h = new HistorialEstado();
        h.setEstado(nuevoEstado);
        h.setFechaCambio(LocalDateTime.now());
        h.setPedido(this);

        agregarHistorial(h);
    }
    
    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
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

    public DetallePedido[] getDetalles() {
        return detalles;
    }

    public void setDetalles(DetallePedido[] detalles) {
        this.detalles = detalles;
    }

    public HistorialEstado[] getHistorialEstados() {
        return historialEstados;
    }

    public void setHistorialEstados(HistorialEstado[] historialEstados) {
        this.historialEstados = historialEstados;
    }

    public int getCantidadDetalles() {
        return cantidadDetalles;
    }

    public int getCantidadHistorial() {
        return cantidadHistorial;
    }
}
