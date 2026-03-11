package servicio;

import acceso_datos.NivelDAO;
import java.sql.SQLException;
import modelo.ConfiguracionNivel;

public class ServicioNivel {

    private final NivelDAO nivelDAO;

    public ServicioNivel() {
        this.nivelDAO = new NivelDAO();
    }

    public ConfiguracionNivel obtenerConfiguracion(int nivel) throws SQLException, ClassNotFoundException {
        return nivelDAO.obtenerPorNivel(nivel);
    }

    public int calcularSiguienteNivel(int nivelActual, int pedidosCompletados, int puntos) throws SQLException, ClassNotFoundException {

        ConfiguracionNivel config = nivelDAO.obtenerPorNivel(nivelActual);

        if (config == null) return nivelActual;

        if (config.getPedidosParaSubir() != null &&
            pedidosCompletados >= config.getPedidosParaSubir()) {

            return nivelActual + 1;
        }

        if (config.getPuntosParaSubir() != null &&
            puntos >= config.getPuntosParaSubir()) {

            return nivelActual + 1;
        }

        return nivelActual;
    }
}