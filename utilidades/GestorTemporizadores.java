package utilidades;

import modelo.EstadoPedido;
import modelo.Pedido;
import servicio.ServicioPedido;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GestorTemporizadores {

    private final Map<Integer, Thread> temporizadoresActivos;
    private final ServicioPedido servicioPedido;

    public GestorTemporizadores() {
        this.temporizadoresActivos = new ConcurrentHashMap<>();
        this.servicioPedido = new ServicioPedido();
    }

    public void iniciarTemporizador(Pedido pedido) {

        Runnable tarea = () -> {

            int tiempoRestante = pedido.getTiempoLimite();

            while (tiempoRestante > 0) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }

                tiempoRestante--;

                // Si el pedido ya fue entregado manualmente, detener
                if (pedido.getEstadoActual().getNombreEstado()
                        .equals(Constantes.ESTADO_ENTREGADA)) {
                    return;
                }
            }

            // Si llega aquí, el tiempo expiró
            try {
                EstadoPedido estadoNoEntregado = new EstadoPedido(6, Constantes.ESTADO_NO_ENTREGADO);

                pedido.setTiempoFinalizacion(LocalDateTime.now());

                servicioPedido.avanzarEstado(pedido, estadoNoEntregado);

            } catch (Exception e) {
                System.err.println("Error al finalizar pedido automáticamente: "
                        + e.getMessage());
            }
        };

        Thread hilo = new Thread(tarea);
        hilo.start();

        temporizadoresActivos.put(pedido.getIdPedido(), hilo);
    }

    public void detenerTemporizador(int idPedido) {

        Thread hilo = temporizadoresActivos.get(idPedido);

        if (hilo != null && hilo.isAlive()) {
            hilo.interrupt();
            temporizadoresActivos.remove(idPedido);
        }
    }
}