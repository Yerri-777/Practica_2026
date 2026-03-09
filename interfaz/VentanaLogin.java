package interfaz;

import servicio.ServicioAutenticacion;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private ServicioAutenticacion servicioAuth;

    public VentanaLogin() {

        servicioAuth = new ServicioAutenticacion();

        setTitle("Pizza Express Tycoon - Login");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fondo
        JLabel fondo = new JLabel();
        fondo.setIcon(new ImageIcon(getClass().getResource("/resources/fondo_login.jpg")));
        fondo.setLayout(new GridBagLayout());
        add(fondo);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setOpaque(false);

        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();

        JButton btnLogin = new JButton("Iniciar Sesión");

        btnLogin.addActionListener(e -> login());

        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPassword);
        panel.add(new JLabel());
        panel.add(btnLogin);

        fondo.add(panel);

        setVisible(true);
    }

    private void login() {
        try {
            Usuario usuario = servicioAuth.login(
                    txtUsuario.getText(),
                    new String(txtPassword.getPassword()));

            dispose();
            new VentanaMenuPrincipal(usuario);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}