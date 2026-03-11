package utilidades;

public class Constantes {

    private Constantes() {
    }

    // Estados del Pedido
    public static final String ESTADO_RECIBIDA = "RECIBIDA";
    public static final String ESTADO_PREPARANDO = "PREPARANDO";
    public static final String ESTADO_EN_HORNO = "EN_HORNO";
    public static final String ESTADO_ENTREGADA = "ENTREGADA";
    public static final String ESTADO_CANCELADA = "CANCELADA";
    public static final String ESTADO_NO_ENTREGADO = "NO_ENTREGADO";

    // Puntajes
    public static final int PUNTOS_PEDIDO_COMPLETADO = 100;
    public static final int PUNTOS_BONUS_EFICIENCIA = 50;
    public static final int PENALIZACION_CANCELADO = -30;
    public static final int PENALIZACION_NO_ENTREGADO = -50;

}
