package acceso_datos;

import modelo.Sucursal;
import java.sql.*;

public class SucursalDAO {

    // =============================
    // OBTENER TODAS LAS SUCURSALES
    // =============================
    public Sucursal[] obtenerTodas() throws SQLException, ClassNotFoundException {

        String sqlCount = "SELECT COUNT(*) FROM sucursal";
        String sql = "SELECT * FROM sucursal";

        try (Connection conn = ConexionBD.getConexion()) {

            int total = 0;

            PreparedStatement stmtCount = conn.prepareStatement(sqlCount);
            ResultSet rsCount = stmtCount.executeQuery();

            if (rsCount.next()) {
                total = rsCount.getInt(1);
            }

            Sucursal[] sucursales = new Sucursal[total];

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            int i = 0;

            while (rs.next()) {

                Sucursal s = new Sucursal();

                s.setIdSucursal(rs.getInt("id_sucursal"));
                s.setNombre(rs.getString("nombre"));
                s.setDireccion(rs.getString("direccion"));
                s.setEstado(rs.getBoolean("estado"));

                sucursales[i] = s;
                i++;
            }

            return sucursales;
        }
    }

    // =============================
    // OBTENER SUCURSAL POR ID
    // =============================
    public Sucursal obtenerPorId(int idSucursal) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM sucursal WHERE id_sucursal = ?";

        try (Connection conn = ConexionBD.getConexion()) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idSucursal);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Sucursal s = new Sucursal();

                s.setIdSucursal(rs.getInt("id_sucursal"));
                s.setNombre(rs.getString("nombre"));
                s.setDireccion(rs.getString("direccion"));
                s.setEstado(rs.getBoolean("estado"));

                return s;
            }

        }

        return null;
    }

    // =============================
    // CREAR SUCURSAL
    // =============================
    public void guardar(Sucursal sucursal) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO sucursal (nombre, direccion, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConexion()) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, sucursal.getNombre());
            stmt.setString(2, sucursal.getDireccion());
            stmt.setBoolean(3, sucursal.isEstado());

            stmt.executeUpdate();
        }
    }

    // =============================
    // ACTUALIZAR SUCURSAL
    // =============================
    public void actualizar(Sucursal sucursal) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE sucursal SET nombre = ?, direccion = ?, estado = ? WHERE id_sucursal = ?";

        try (Connection conn = ConexionBD.getConexion()) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, sucursal.getNombre());
            stmt.setString(2, sucursal.getDireccion());
            stmt.setBoolean(3, sucursal.isEstado());
            stmt.setInt(4, sucursal.getIdSucursal());

            stmt.executeUpdate();
        }
    }
}
