package modelo;

public class PedidoPizza extends Pedido {

    private boolean enHorno;

    public PedidoPizza() {
        super();
        enHorno = false;
    }

    public void meterAlHorno() {

        enHorno = true;

        cambiarEstado(EstadoPedido.EN_HORNO);

    }

    public boolean isEnHorno() {
        return enHorno;
    }

}
