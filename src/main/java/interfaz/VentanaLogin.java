package interfaz;

import servicio.ServicioAutenticacion;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class VentanaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private ServicioAutenticacion servicioAuth;

    public VentanaLogin() {
        servicioAuth = new ServicioAutenticacion();

        setTitle("Pizza Express Tycoon - Login");
        setSize(500, 400); // Tamaño medio definido
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel fondo = new JLabel();
        fondo.setLayout(new GridBagLayout());

        //cargar el logo de la pizza
        URL imgURL = getClass().getResource("/Logo_pizza.png");

        if (imgURL != null) {
          
            ImageIcon iconoOriginal = new ImageIcon(imgURL);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH);
            fondo.setIcon(new ImageIcon(imagenEscalada));
        } else {
            System.out.println("No se encontró la imagen en: /Logo_pizza.png");
            fondo.setOpaque(true);
            fondo.setBackground(Color.LIGHT_GRAY);
        }

        add(fondo);


        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setOpaque(false); 

  
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setForeground(Color.BLACK);
        lblUser.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.BLACK);
        lblPass.setFont(new Font("Arial", Font.BOLD, 24));

        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Iniciar Sesión");

        btnLogin.addActionListener(e -> login());

        panel.add(lblUser);
        panel.add(txtUsuario);
        panel.add(lblPass);
        panel.add(txtPassword);
        panel.add(new JLabel());
        panel.add(btnLogin);

        fondo.add(panel);
        setVisible(true);
    }

   private void login() {

    String usuarioTxt = txtUsuario.getText().trim();
    String passwordTxt = new String(txtPassword.getPassword());

    if (usuarioTxt.isEmpty() || passwordTxt.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña");
        return;
    }

    try {

        Usuario usuario = servicioAuth.login(usuarioTxt, passwordTxt);

        if (usuario != null) {

            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getUsername());

            dispose();

            String rol = usuario.getRol().getNombre().toUpperCase();
            System.out.println("ROL DEL USUARIO: " + rol);
            
            switch (rol) {

                case "JUGADOR":

                    new VentanaMenuPrincipal(usuario).setVisible(true);
                    break;

                case "ADMIN":

                    new VentanaAdministrador(usuario).setVisible(true);
                    break;

                case "ADMIN_TIENDA":

                    new VentanaSuperAdministrador(usuario).setVisible(true);
                    break;

                default:

                    JOptionPane.showMessageDialog(this, "Rol no reconocido: " + rol);
            }

        } else {

            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");

        }

    } catch (Exception e) {

        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al iniciar el sistema");

        }
    }
   
}
