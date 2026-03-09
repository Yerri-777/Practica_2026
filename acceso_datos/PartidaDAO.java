package acceso_datos;

import modelo.Partida;

import java.sql.*;

public class PartidaDAO {

    public int guardar(Partida partida) {

        String sql = "INSERT INTO partida(fecha_inicio,puntaje_final,nivel_alcanzado,id_usuario,id_sucursal) VALUES(?,?,?,?,?)";

        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(partida.getFechaInicio()));
            stmt.setInt(2, partida.getPuntajeFinal());
            stmt.setInt(3, partida.getNivelAlcanzado());
            stmt.setInt(4, partida.getUsuario().getIdUsuario());
            stmt.setInt(5, partida.getSucursal().getIdSucursal());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void finalizarPartida(Partida partida) {

        String sql = "UPDATE partida SET fecha_fin=?, puntaje_final=?, nivel_alcanzado=? WHERE id_partida=?";

        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(partida.getFechaFin()));
            stmt.setInt(2, partida.getPuntajeFinal());
            stmt.setInt(3, partida.getNivelAlcanzado());
            stmt.setInt(4, partida.getIdPartida());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}