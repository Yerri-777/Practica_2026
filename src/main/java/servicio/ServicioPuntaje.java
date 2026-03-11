package servicio;

import modelo.Pedido;

import java.time.Duration;
public class ServicioPuntaje {

    public int calcularPuntajePedido(Pedido pedido) {

        String estado = pedido.getEstadoActual().getNombreEstado();

        if (estado.equals("CANCELADA")) {
            return -40;
        }

        if (estado.equals("NO_ENTREGADO")) {
            return -60;
        }

        if (estado.equals("ENTREGADA")) {

            int puntos = 100;

            long tiempoUsado = Duration.between(
                    pedido.getTiempoCreacion(),
                    pedido.getTiempoFinalizacion()
            ).getSeconds();

            if (tiempoUsado < pedido.getTiempoLimite() * 0.5) {
                puntos += 50;
            }

            if (tiempoUsado < pedido.getTiempoLimite() * 0.25) {
                puntos += 50;
            }

            return puntos;
        }

        return 0;
    }
}