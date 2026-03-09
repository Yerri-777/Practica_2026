package acceso_datos;

import modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listarActivosPorSucursal(int idSucursal) {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT * FROM producto WHERE activo = TRUE AND id_sucursal = ?";

        try (Connection conn = ConexionBD.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSucursal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Producto producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getBigDecimal("precio"),
                        rs.getBoolean("activo"),
                        null);

                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
}