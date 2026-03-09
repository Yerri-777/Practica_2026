package acceso_datos;

import modelo.Pedido;

import java.sql.*;

public class PedidoDAO {

    public int guardar(Pedido pedido) {

        String sql = "INSERT INTO pedido(tiempo_limite,tiempo_creacion,estado_actual,id_partida) VALUES(?,?,?,?)";

        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getTiempoLimite());
            stmt.setTimestamp(2, Timestamp.valueOf(pedido.getTiempoCreacion()));
            stmt.setString(3, pedido.getEstadoActual().getNombreEstado());
            stmt.setInt(4, pedido.getPartida().getIdPartida());

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

    public void actualizarEstado(int idPedido, String nuevoEstado) {

        String sql = "UPDATE pedido SET estado_actual=? WHERE id_pedido=?";

        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idPedido);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}