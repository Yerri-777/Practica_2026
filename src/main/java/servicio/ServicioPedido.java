package servicio;

import acceso_datos.PedidoDAO;
import java.sql.SQLException;
import modelo.*;

import java.time.LocalDateTime;
import java.util.Random;

public class ServicioPedido {

    private final PedidoDAO pedidoDAO;

    public ServicioPedido() {
        this.pedidoDAO = new PedidoDAO();
    }

    public Pedido generarPedido(Partida partida, int nivel) {
        Random random = new Random();

        int tiempoLimite = 60 - (nivel * 5);
        if (tiempoLimite < 20) {
            tiempoLimite = 20;
        }

        Pedido pedido = new Pedido();
        pedido.setTiempoLimite(tiempoLimite);
        pedido.setPartida(partida);
        pedido.setEstadoActual(EstadoPedido.PENDIENTE);
        pedido.setTiempoCreacion(LocalDateTime.now());

        // Generar pizza aleatoria
        Pizza pizza = generarPizzaRandom();
        pedido.setPizza(pizza);

        return pedido;
    }

    private Pizza generarPizzaRandom() {
        Random r = new Random();
        int tipo = r.nextInt(4);

        // Cambio de List.of() por arreglos tradicionales new String[] {...}
        if (tipo == 0) {
            return new Pizza(
                    "Pizza Clasica",
                    "/resources/pizza_clasica.png",
                    new String[]{"Tomate", "Queso"}
            );
        }

        if (tipo == 1) {
            return new Pizza(
                    "Pizza Pepperoni",
                    "/resources/pizza_pepperoni.png",
                    new String[]{"Tomate", "Queso", "Pepperoni"}
            );
        }

        if (tipo == 2) {
            return new Pizza(
                    "Pizza Champiñones",
                    "/resources/pizza_champi.png",
                    new String[]{"Tomate", "Queso", "Champiñones"}
            );
        }

        return new Pizza(
                "Pizza Especial",
                "/resources/pizza_especial.png",
                new String[]{"Tomate", "Queso", "Pepperoni", "Champiñones"}
        );
    }

    public void avanzarEstado(Pedido pedido, EstadoPedido siguienteEstado) throws SQLException, ClassNotFoundException {
        String estadoNombre = pedido.getEstadoActual().getNombreEstado();

        if (estadoNombre.equals("ENTREGADA")
                || estadoNombre.equals("CANCELADA")
                || estadoNombre.equals("NO_ENTREGADO")) {

            throw new RuntimeException("Pedido ya finalizado.");
        }

        validarTransicion(estadoNombre, siguienteEstado.getNombreEstado());

        pedido.setEstadoActual(siguienteEstado);

        if (siguienteEstado.getNombreEstado().equals("ENTREGADA")) {
            pedido.setTiempoFinalizacion(LocalDateTime.now());
        }

        pedidoDAO.actualizarEstado(
                pedido.getIdPedido(),
                siguienteEstado.getNombreEstado());
    }

    private void validarTransicion(String actual, String siguiente) {
        if (actual.equals("RECIBIDA") && !siguiente.equals("PREPARANDO")) {
            throw new RuntimeException("Transición inválida.");
        }

        if (actual.equals("PREPARANDO") && !siguiente.equals("EN_HORNO")) {
            throw new RuntimeException("Transición inválida.");
        }

        if (actual.equals("EN_HORNO") && !siguiente.equals("ENTREGADA")) {
            throw new RuntimeException("Transición inválida.");
        }
    }
}
