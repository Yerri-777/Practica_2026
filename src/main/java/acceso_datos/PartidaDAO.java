package acceso_datos;

import modelo.Partida;
import java.sql.*;
import modelo.Usuario;

public class PartidaDAO {

    public int guardar(Partida partida) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO partida(fecha_inicio,puntaje_final,nivel_alcanzado,id_usuario,id_sucursal) VALUES(?,?,?,?,?)";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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

        }

        return -1;
    }

    public void finalizarPartida(Partida partida) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE partida SET fecha_fin=?, puntaje_final=?, nivel_alcanzado=? WHERE id_partida=?";

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(partida.getFechaFin()));
            stmt.setInt(2, partida.getPuntajeFinal());
            stmt.setInt(3, partida.getNivelAlcanzado());
            stmt.setInt(4, partida.getIdPartida());

            stmt.executeUpdate();
        }
    }

    public Partida[] obtenerTodas() throws Exception {
        String sqlCount = "SELECT COUNT(*) FROM partida";
        String sqlData = "SELECT id_partida, puntaje_final FROM partida";

        Connection conn = ConexionBD.getConexion();

        // 1. Obtener el total para definir el tamaño del array
        PreparedStatement stmtCount = conn.prepareStatement(sqlCount);
        ResultSet rsCount = stmtCount.executeQuery();
        rsCount.next();
        int total = rsCount.getInt(1);

        Partida[] lista = new Partida[total];

        // 2. Llenar el array
        PreparedStatement stmtData = conn.prepareStatement(sqlData);
        ResultSet rs = stmtData.executeQuery();

        int i = 0;
        while (rs.next()) {
            Partida p = new Partida();
            p.setIdPartida(rs.getInt("id_partida"));
            p.setPuntajeFinal(rs.getInt("puntaje_final"));
            lista[i] = p;
            i++;
        }

        // Cerrar recursos
        rs.close();
        stmtData.close();
        rsCount.close();
        stmtCount.close();
        conn.close();

        return lista;
    }

    

    public Partida[] obtenerPorSucursal(int idSucursal) throws SQLException, ClassNotFoundException {

        Partida[] partidas;

        String sqlContar = "SELECT COUNT(*) FROM partida WHERE id_sucursal = ?";

        String sqlData = """
        SELECT p.*, u.username
         FROM partida p
            JOIN usuario u ON p.id_usuario = u.id_usuario
            WHERE p.id_sucursal = ?
            """;

        try (Connection con = ConexionBD.getConexion()) {

            PreparedStatement psCount = con.prepareStatement(sqlContar);
            psCount.setInt(1, idSucursal);

            ResultSet rsCount = psCount.executeQuery();

            int total = 0;

            if (rsCount.next()) {
                total = rsCount.getInt(1);
            }

            partidas = new Partida[total];

            PreparedStatement psData = con.prepareStatement(sqlData);
            psData.setInt(1, idSucursal);

            ResultSet rs = psData.executeQuery();

            int i = 0;

            while (rs.next()) {

                Partida p = new Partida();

                p.setIdPartida(rs.getInt("id_partida"));
                p.setPuntajeFinal(rs.getInt("puntaje_final"));
                p.setNivelAlcanzado(rs.getInt("nivel_alcanzado"));

                Usuario u = new Usuario();
                u.setUsername(rs.getString("username"));

                p.setUsuario(u);

                partidas[i] = p;
                i++;
            }
        }

        return partidas;
    }

}
