package acceso_datos;

import modelo.*;

import java.sql.*;

public class UsuarioDAO {

    public Usuario buscarPorUsername(String username) {

        String sql = """
                SELECT u.*, r.id_rol, r.nombre AS rol_nombre, r.descripcion,
                       s.id_sucursal, s.nombre AS sucursal_nombre, s.direccion, s.estado
                FROM usuario u
                JOIN rol r ON u.id_rol = r.id_rol
                LEFT JOIN sucursal s ON u.id_sucursal = s.id_sucursal
                WHERE u.username = ?
                """;

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Rol rol = new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("rol_nombre"),
                        rs.getString("descripcion")
                );

                Sucursal sucursal = null;
                if (rs.getInt("id_sucursal") != 0) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean guardar(Usuario usuario) {

        String sql = "INSERT INTO usuario(username,password,nombre_completo,estado,id_rol,id_sucursal) VALUES(?,?,?,?,?,?)";

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getNombreCompleto());
            stmt.setBoolean(4, usuario.isEstado());
            stmt.setInt(5, usuario.getRol().getIdRol());
            stmt.setInt(6, usuario.getSucursal().getIdSucursal());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}