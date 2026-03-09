package acceso_datos;

import modelo.ConfiguracionNivel;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class NivelDAO {

    public ConfiguracionNivel obtenerPorNivel(int nivel) {

        String sql = "SELECT * FROM nivel_configuracion WHERE nivel = ?";

        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nivel);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ConfiguracionNivel(
                        rs.getInt("id_nivel"),
                        rs.getInt("nivel"),
                        rs.getInt("tiempo_base_segundos"),
                        rs.getObject("pedidos_para_subir", Integer.class),
                        rs.getObject("puntos_para_subir", Integer.class));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}