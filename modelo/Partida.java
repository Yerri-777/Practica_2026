package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Partida {

    private int idPartida;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int puntajeFinal;
    private int nivelAlcanzado;

    private Usuario usuario;
    private Sucursal sucursal;

    private List<Pedido> pedidos;

    public Partida() {
        this.pedidos = new ArrayList<>();
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
        this.pedidos = new ArrayList<>();
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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}