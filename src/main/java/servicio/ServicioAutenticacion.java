package servicio;

import acceso_datos.UsuarioDAO;
import java.sql.SQLException;
import modelo.Usuario;

public class ServicioAutenticacion {

    private final UsuarioDAO usuarioDAO;

    public ServicioAutenticacion() {
        usuarioDAO = new UsuarioDAO();
    }

    public Usuario login(String username, String passwordPlano)
            throws SQLException, ClassNotFoundException {

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            return null;
        }

        if (!usuario.isEstado()) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!passwordPlano.equals(usuario.getPassword())) {
            return null;
        }

        return usuario;
    }

}
