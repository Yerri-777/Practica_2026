package interfaz;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaMenuPrincipal extends JFrame {

    private Usuario usuario;

    public VentanaMenuPrincipal(Usuario usuario) {

        this.usuario = usuario;

        setTitle("Menú Principal");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel logo = new JLabel();
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setIcon(new ImageIcon(getClass().getResource("/resources/logo.png")));
        add(logo, BorderLayout.NORTH);

        JPanel panel = new JPanel();

        JButton btnJugar = new JButton("Iniciar Partida");
        JButton btnRanking = new JButton("Ranking");
        JButton btnSalir = new JButton("Salir");

        btnJugar.addActionListener(e -> new VentanaJuego(usuario));
        btnRanking.addActionListener(e -> new VentanaRanking());

        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(btnJugar);
        panel.add(btnRanking);
        panel.add(btnSalir);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}