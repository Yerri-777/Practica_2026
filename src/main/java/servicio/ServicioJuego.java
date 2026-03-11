package servicio;

import acceso_datos.PartidaDAO;
import acceso_datos.PedidoDAO;
import java.sql.SQLException;
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

    public Partida iniciarPartida(Usuario usuario) throws SQLException, ClassNotFoundException {
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

    public void registrarPedidoEntregado(Partida partida) {

        partida.setPedidosCorrectos(partida.getPedidosCorrectos() + 1);

        int nuevosPuntos = partida.getPuntajeFinal() + 100;
        partida.setPuntajeFinal(nuevosPuntos);

        int nivelActual = partida.getNivelAlcanzado();

        if (nivelActual == 1
                && (partida.getPedidosCorrectos() >= 5 || nuevosPuntos >= 500)) {

            partida.setNivelAlcanzado(2);
        }

        if (nivelActual == 2
                && (partida.getPedidosCorrectos() >= 10 || nuevosPuntos >= 1000)) {

            partida.setNivelAlcanzado(3);
        }
    }
    
    public Pedido generarPedido(Partida partida, int nivelActual) throws SQLException, ClassNotFoundException {
        ConfiguracionNivel config = servicioNivel.obtenerConfiguracion(nivelActual);

        Pedido pedido = new Pedido();
        pedido.setTiempoLimite(config.getTiempoBaseSegundos());
        pedido.setTiempoCreacion(LocalDateTime.now());
        pedido.setEstadoActual(new EstadoPedido(1, "RECIBIDA"));
        pedido.setPartida(partida);

        // Generar la pizza ANTES de guardar o retornar
        Pizza pizzaAleatoria = PizzaFactory.generarPizza();
        pedido.setPizza(pizzaAleatoria); // Verifica que Pedido.java tenga setPizza()

        int idPedido = pedidoDAO.guardar(pedido);
        pedido.setIdPedido(idPedido);

        return pedido; // El return siempre al final
    }

    public void finalizarPartida(Partida partida) throws SQLException, ClassNotFoundException {
        partida.setFechaFin(LocalDateTime.now());
        partidaDAO.finalizarPartida(partida);
    }
}
