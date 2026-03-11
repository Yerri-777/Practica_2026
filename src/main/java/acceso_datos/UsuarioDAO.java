package acceso_datos;

import modelo.*;
import java.sql.*;

public class UsuarioDAO {

    public Usuario buscarPorUsername(String username)
            throws SQLException, ClassNotFoundException {

        String sql = """
        SELECT u.*,r.id_rol,r.nombre AS rol_nombre,r.descripcion,
               s.id_sucursal,s.nombre AS sucursal_nombre,s.direccion,s.estado
        FROM usuario u
        JOIN rol r ON u.id_rol=r.id_rol
        LEFT JOIN sucursal s ON u.id_sucursal=s.id_sucursal
        WHERE u.username=?
        """;

        try (Connection conn = ConexionBD.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol(
                            rs.getInt("id_rol"),
                            rs.getString("rol_nombre"),
                            rs.getString("descripcion")
                    );

                    Sucursal sucursal = null;
                    if (rs.getObject("id_sucursal") != null) {
                        sucursal = new Sucursal(
                                rs.getInt("id_sucursal"),
                                rs.getString("sucursal_nombre"),
                                rs.getString("direccion"),
                                rs.getBoolean("estado")
                        );
                    }

                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("nombre_completo"),
                            rs.getBoolean("estado"),
                            rol,
                            sucursal
                    );
                }
            }
        }
        return null;
    }

    public Usuario[] rankingSucursal(int idSucursal)
            throws SQLException, ClassNotFoundException {

        String sqlContar = "SELECT COUNT(DISTINCT u.username) FROM usuario u JOIN partida p ON p.id_usuario=u.id_usuario WHERE u.id_sucursal=?";
        String sqlData = """
        SELECT u.username, MAX(p.puntaje_final) AS mejor_puntaje
        FROM usuario u JOIN partida p ON p.id_usuario=u.id_usuario
        WHERE u.id_sucursal=? GROUP BY u.username ORDER BY mejor_puntaje DESC
        """;

        try (Connection conn = ConexionBD.getConexion()) {
            // Contar registros
            int total = 0;
            try (PreparedStatement stC = conn.prepareStatement(sqlContar)) {
                stC.setInt(1, idSucursal);
                ResultSet rsC = stC.executeQuery();
                if (rsC.next()) {
                    total = rsC.getInt(1);
                }
            }

            // Llenar arreglo
            Usuario[] ranking = new Usuario[total];
            try (PreparedStatement stD = conn.prepareStatement(sqlData)) {
                stD.setInt(1, idSucursal);
                ResultSet rs = stD.executeQuery();
                int i = 0;
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setUsername(rs.getString("username"));
                    u.setMejorPuntaje(rs.getInt("mejor_puntaje"));
                    ranking[i++] = u;
                }
            }
            return ranking;
        }
    }

    public Usuario[] rankingGlobal()
            throws SQLException, ClassNotFoundException {

        String sqlContar = "SELECT COUNT(DISTINCT u.username) FROM usuario u JOIN partida p ON p.id_usuario=u.id_usuario";
        String sqlData = """
        SELECT u.username, MAX(p.puntaje_final) AS mejor_puntaje
        FROM usuario u JOIN partida p ON p.id_usuario=u.id_usuario
        GROUP BY u.username ORDER BY mejor_puntaje DESC
        """;

        try (Connection conn = ConexionBD.getConexion()) {
            int total = 0;
            try (Statement stC = conn.createStatement()) {
                ResultSet rsC = stC.executeQuery(sqlContar);
                if (rsC.next()) {
                    total = rsC.getInt(1);
                }
            }

            Usuario[] ranking = new Usuario[total];
            try (Statement stD = conn.createStatement()) {
                ResultSet rs = stD.executeQuery(sqlData);
                int i = 0;
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setUsername(rs.getString("username"));
                    u.setMejorPuntaje(rs.getInt("mejor_puntaje"));
                    ranking[i++] = u;
                }
            }
            return ranking;
        }
    }
    
    public Usuario[] obtenerTodos() throws Exception {

        String sql = "SELECT * FROM usuario";

        Connection conn = ConexionBD.getConexion();

        PreparedStatement stmt = conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        java.util.List<Usuario> lista = new java.util.ArrayList<>();

        while (rs.next()) {

            Usuario u = new Usuario();

            u.setIdUsuario(rs.getInt("id_usuario"));
            u.setUsername(rs.getString("username"));

            lista.add(u);
        }

        rs.close();
        stmt.close();
        conn.close();

        return lista.toArray(new Usuario[0]);
    }

    public void asignarSucursal(int idUsuario, int idSucursal) throws Exception {

        String sql = "UPDATE usuario SET id_sucursal = ? WHERE id_usuario = ?";

        Connection conn = ConexionBD.getConexion();

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, idSucursal);
        stmt.setInt(2, idUsuario);

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public boolean existeUsuario(String username) throws Exception {

        String sql = "SELECT COUNT(*) FROM usuario WHERE username = ?";

        Connection conn = ConexionBD.getConexion();

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        boolean existe = false;

        if (rs.next()) {

            existe = rs.getInt(1) > 0;

        }

        rs.close();
        stmt.close();
        conn.close();

        return existe;
    }
    
    public void guardar(Usuario u) throws Exception {

        String sql = "INSERT INTO usuario (username, password, id_rol) VALUES (?, ?, ?)";

        Connection conn = ConexionBD.getConexion();

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, u.getUsername());
        stmt.setString(2, u.getPassword());
        stmt.setInt(3, u.getRol().getIdRol());

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
