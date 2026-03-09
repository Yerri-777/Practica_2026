package servicio;

import acceso_datos.UsuarioDAO;
import modelo.Usuario;
import utilidades.UtilidadPassword;

public class ServicioAutenticacion {

    private final UsuarioDAO usuarioDAO;

    public ServicioAutenticacion() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario login(String username, String passwordPlano) {

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        if (!usuario.isEstado()) {
            throw new RuntimeException("Usuario inactivo.");
        }

        if (!UtilidadPassword.verificar(passwordPlano, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        return usuario;
    }
}