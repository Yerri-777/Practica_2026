package interfaz;

import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;

public class VentanaMenuPrincipal extends JFrame {

    private Usuario usuario;

    public VentanaMenuPrincipal(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Menú Principal - Pizza Tycoon");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // cargar el logo
        URL logoURL = getClass().getResource("/logo.jpg");
        Image imagenFondo = (logoURL != null) ? new ImageIcon(logoURL).getImage() : null;

        // personalizo el panel para darle mas vista al jugador
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        panelFondo.setLayout(new GridBagLayout()); 
        setContentPane(panelFondo);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false); 
        panelBotones.setLayout(new GridLayout(3, 1, 15, 15));
        panelBotones.setPreferredSize(new Dimension(250, 200));

        JButton btnJugar = new JButton("Iniciar Partida");
        JButton btnRanking = new JButton("Ranking");
        JButton btnSalir = new JButton("Salir");

        estilizarBoton(btnJugar);
        estilizarBoton(btnRanking);
        estilizarBoton(btnSalir);

        // eventos

        btnJugar.addActionListener(e -> {
            try {
                new VentanaJuego(usuario).setVisible(true);
                dispose();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(VentanaMenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Error al iniciar el juego");
            }
        });
        
        btnRanking.addActionListener(e -> {
            try {
                VentanaRanking vr = new VentanaRanking(usuario);
                vr.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al abrir el ranking");
            }
        });
        
        btnSalir.addActionListener(e -> {

            new VentanaLogin().setVisible(true);

            dispose();

        });

        panelBotones.add(btnJugar);
        panelBotones.add(btnRanking);
        panelBotones.add(btnSalir);

        panelFondo.add(panelBotones); 

        setVisible(true);
    }

    private void estilizarBoton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(255, 255, 255, 200));
    }
}
