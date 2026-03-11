package servicio;

import acceso_datos.PedidoDAO;
import modelo.*;

import java.time.LocalDateTime;

public class ServicioPedido {

    private final PedidoDAO pedidoDAO;

    public ServicioPedido() {
        this.pedidoDAO = new PedidoDAO();
    }

    public void avanzarEstado(Pedido pedido, EstadoPedido siguienteEstado) {

        if (pedido.getEstadoActual().getNombreEstado().equals("ENTREGADA") ||
                pedido.getEstadoActual().getNombreEstado().equals("CANCELADA") ||
                pedido.getEstadoActual().getNombreEstado().equals("NO_ENTREGADO")) {

            throw new RuntimeException("Pedido ya finalizado.");
        }

        validarTransicion(pedido.getEstadoActual().getNombreEstado(),
                siguienteEstado.getNombreEstado());

        pedido.setEstadoActual(siguienteEstado);

        if (siguienteEstado.getNombreEstado().equals("ENTREGADA")) {
            pedido.setTiempoFinalizacion(LocalDateTime.now());
        }

        pedidoDAO.actualizarEstado(
                pedido.getIdPedido(),
                siguienteEstado.getNombreEstado());
    }

    private void validarTransicion(String actual, String siguiente) {

        if (actual.equals("RECIBIDA") && !siguiente.equals("PREPARANDO"))
            throw new RuntimeException("Transición inválida.");

        if (actual.equals("PREPARANDO") && !siguiente.equals("EN_HORNO"))
            throw new RuntimeException("Transición inválida.");

        if (actual.equals("EN_HORNO") && !siguiente.equals("ENTREGADA"))
            throw new RuntimeException("Transición inválida.");
    }
}