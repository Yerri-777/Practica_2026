package acceso_datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/pizza_tycoon";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    private static Connection conexion;

    private ConexionBD() {
    }

    public static Connection getConexion() throws SQLException, ClassNotFoundException {

        if (conexion == null || conexion.isClosed()) {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        }

        return conexion;
    }
}
