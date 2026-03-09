package interfaz;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaJuego extends JFrame {

    private Usuario usuario;
    private JLabel lblPuntaje;

    public VentanaJuego(Usuario usuario) {

        this.usuario = usuario;

        setTitle("Juego - Pizza Express Tycoon");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fondo
        JLabel fondo = new JLabel();
        fondo.setIcon(new ImageIcon(getClass().getResource("/resources/fondo_juego.jpg")));
        fondo.setLayout(new BorderLayout());
        add(fondo);

        lblPuntaje = new JLabel("Puntaje: 0");
        lblPuntaje.setFont(new Font("Arial", Font.BOLD, 20));

        fondo.add(lblPuntaje, BorderLayout.NORTH);

        JPanel panelPedidos = new JPanel();
        panelPedidos.setBorder(BorderFactory.createTitledBorder("Pedidos Activos"));
        fondo.add(panelPedidos, BorderLayout.CENTER);

        setVisible(true);
    }
}