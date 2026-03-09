package servicio;

import acceso_datos.PartidaDAO;
import acceso_datos.PedidoDAO;
import modelo.*;

import java.time.LocalDateTime;

public class ServicioJuego {

    private final PartidaDAO partidaDAO;
    private final PedidoDAO pedidoDAO;
    private final ServicioNivel servicioNivel;

    public ServicioJuego() {
        this.partidaDAO = new PartidaDAO();
        this.pedidoDAO = new PedidoDAO();
        this.servicioNivel = new ServicioNivel();
    }

    public Partida iniciarPartida(Usuario usuario) {

        Partida partida = new Partida();
        partida.setFechaInicio(LocalDateTime.now());
        partida.setNivelAlcanzado(1);
        partida.setPuntajeFinal(0);
        partida.setUsuario(usuario);
        partida.setSucursal(usuario.getSucursal());

        int idGenerado = partidaDAO.guardar(partida);
        partida.setIdPartida(idGenerado);

        return partida;
    }

    public Pedido generarPedido(Partida partida, int nivelActual) {

        ConfiguracionNivel config = servicioNivel.obtenerConfiguracion(nivelActual);

        Pedido pedido = new Pedido();
        pedido.setTiempoLimite(config.getTiempoBaseSegundos());
        pedido.setTiempoCreacion(LocalDateTime.now());
        pedido.setEstadoActual(new EstadoPedido(1, "RECIBIDA"));
        pedido.setPartida(partida);

        int idPedido = pedidoDAO.guardar(pedido);
        pedido.setIdPedido(idPedido);

        return pedido;
    }

    public void finalizarPartida(Partida partida) {
        partida.setFechaFin(LocalDateTime.now());
        partidaDAO.finalizarPartida(partida);
    }
}