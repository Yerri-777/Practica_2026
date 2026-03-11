package utilidades;

public class ClienteFactory {

    private static String[] nombres = {
        "Carlos","Ana","Luis","Maria","Jose",
        "Lucia","Pedro","Sofia","Miguel","Laura"
    };

    public static String generarCliente(){

        int i = (int)(Math.random() * nombres.length);

        return nombres[i];
    }
}