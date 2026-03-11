package modelo;

import java.time.LocalDateTime;

public class Partida {

    private int idPartida;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int puntajeFinal;
    private int nivelAlcanzado;

    private Usuario usuario;
    private Sucursal sucursal;

    private int pedidosCorrectos;
    private int pedidosCancelados;
    private int pedidosTarde;

    
    private Pedido[] pedidos;
    private int cantidadPedidos;

    public Partida() {
     
        this.pedidos = new Pedido[100];
        this.cantidadPedidos = 0;
    }

    public Partida(int idPartida, LocalDateTime fechaInicio, LocalDateTime fechaFin,
            int puntajeFinal, int nivelAlcanzado,
            Usuario usuario, Sucursal sucursal) {
        this.idPartida = idPartida;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.puntajeFinal = puntajeFinal;
        this.nivelAlcanzado = nivelAlcanzado;
        this.usuario = usuario;
        this.sucursal = sucursal;
        this.pedidos = new Pedido[100];
        this.cantidadPedidos = 0;
    }

    public int getPedidosActivos() {

        int activos = 0;

        for (int i = 0; i < cantidadPedidos; i++) {

            EstadoPedido estado = pedidos[i].getEstadoActual();

            if (!estado.getNombreEstado().equals("ENTREGADA")
                    && !estado.getNombreEstado().equals("CANCELADA")) {

                activos++;
            }
        }

        return activos;
    }
    
    
    public void agregarPedido(Pedido p) {
        if (cantidadPedidos < pedidos.length) {
            this.pedidos[cantidadPedidos] = p;
            this.cantidadPedidos++;
        }
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getPuntajeFinal() {
        return puntajeFinal;
    }

    public void setPuntajeFinal(int puntajeFinal) {
        this.puntajeFinal = puntajeFinal;
    }

    public int getNivelAlcanzado() {
        return nivelAlcanzado;
    }

    public void setNivelAlcanzado(int nivelAlcanzado) {
        this.nivelAlcanzado = nivelAlcanzado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Pedido[] getPedidos() {
        return pedidos;
    }

    public int getCantidadPedidos() {
        return cantidadPedidos;
    }

    public int getPedidosCorrectos() {
        return pedidosCorrectos;
    }

    public void setPedidosCorrectos(int pedidosCorrectos) {
        this.pedidosCorrectos = pedidosCorrectos;
    }

    public int getPedidosCancelados() {
        return pedidosCancelados;
    }

    public void setPedidosCancelados(int pedidosCancelados) {
        this.pedidosCancelados = pedidosCancelados;
    }

    public int getPedidosTarde() {
        return pedidosTarde;
    }

    public void setPedidosTarde(int pedidosTarde) {
        this.pedidosTarde = pedidosTarde;
    }

    public void setPedidos(Pedido[] pedidos) {
        this.pedidos = pedidos;
        // Si asignamos un arreglo completo, actualizamos el contador basado en elementos no nulos
        this.cantidadPedidos = 0;
        if (pedidos != null) {
            for (Pedido p : pedidos) {
                if (p != null) this.cantidadPedidos++;
            }
        }
    }
}
