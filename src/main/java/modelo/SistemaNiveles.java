package modelo;

public class SistemaNiveles {

    public static final int NIVEL_MAXIMO = 3;

    public static int calcularNivel(int puntaje, int pedidosCorrectos) {

        if (puntaje >= 1000 || pedidosCorrectos >= 10) {
            return 3;
        }

        if (puntaje >= 500 || pedidosCorrectos >= 5) {
            return 2;
        }

        return 1;
    }

}