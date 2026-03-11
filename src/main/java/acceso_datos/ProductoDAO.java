package acceso_datos;

import modelo.Producto;
import modelo.Sucursal;
import java.sql.*;

public class ProductoDAO {

    public Producto[] obtenerPorSucursal(int idSucursal)
            throws SQLException, ClassNotFoundException {

        String sqlContar = "SELECT COUNT(*) FROM producto WHERE id_sucursal=?";
        String sqlData = "SELECT * FROM producto WHERE id_sucursal=?";

        try (Connection conn = ConexionBD.getConexion()) {

            int total = 0;

            try (PreparedStatement stmtCount = conn.prepareStatement(sqlContar)) {

                stmtCount.setInt(1, idSucursal);
                ResultSet rsCount = stmtCount.executeQuery();

                if (rsCount.next()) {
                    total = rsCount.getInt(1);
                }
            }

            Producto[] productos = new Producto[total];

            try (PreparedStatement stmtData = conn.prepareStatement(sqlData)) {

                stmtData.setInt(1, idSucursal);
                ResultSet rs = stmtData.executeQuery();

                int i = 0;

                while (rs.next()) {

                    Sucursal suc = new Sucursal();
                    suc.setIdSucursal(rs.getInt("id_sucursal"));

                    productos[i] = new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getBigDecimal("precio"),
                            rs.getBoolean("activo"),
                            suc
                    );

                    i++;
                }
            }

            return productos;
        }
    }

    public boolean guardar(Producto producto)
            throws SQLException, ClassNotFoundException {

        String sql = """
        INSERT INTO producto(nombre,descripcion,precio,activo,id_sucursal)
        VALUES(?,?,?,?,?)
        """;

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setBigDecimal(3, producto.getPrecio());
            stmt.setBoolean(4, producto.isActivo());
            stmt.setInt(5, producto.getSucursal().getIdSucursal());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Producto producto)
            throws SQLException, ClassNotFoundException {

        String sql = """
        UPDATE producto
        SET nombre=?, precio=?, descripcion=?
        WHERE id_producto=?
        """;

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setBigDecimal(2, producto.getPrecio());
            stmt.setString(3, producto.getDescripcion());
            stmt.setInt(4, producto.getIdProducto());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean cambiarEstado(int idProducto)
            throws SQLException, ClassNotFoundException {

        String sql = """
        UPDATE producto
        SET activo = NOT activo
        WHERE id_producto=?
        """;

        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);

            return stmt.executeUpdate() > 0;
        }
    }

    public Producto[] listarActivosPorSucursal(int idSucursal)
            throws SQLException, ClassNotFoundException {

        String sqlContar = "SELECT COUNT(*) FROM producto WHERE activo=TRUE AND id_sucursal=?";
        String sqlData = "SELECT * FROM producto WHERE activo=TRUE AND id_sucursal=?";

        try (Connection conn = ConexionBD.getConexion()) {

            int total = 0;

            try (PreparedStatement stmtCount = conn.prepareStatement(sqlContar)) {

                stmtCount.setInt(1, idSucursal);

                ResultSet rsCount = stmtCount.executeQuery();

                if (rsCount.next()) {
                    total = rsCount.getInt(1);
                }
            }

            Producto[] productos = new Producto[total];

            try (PreparedStatement stmtData = conn.prepareStatement(sqlData)) {

                stmtData.setInt(1, idSucursal);

                ResultSet rs = stmtData.executeQuery();

                int i = 0;

                while (rs.next()) {

                    Sucursal suc = new Sucursal();
                    suc.setIdSucursal(rs.getInt("id_sucursal"));

                    productos[i] = new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getBigDecimal("precio"),
                            rs.getBoolean("activo"),
                            suc
                    );

                    i++;
                }
            }

            return productos;
        }
    }
}