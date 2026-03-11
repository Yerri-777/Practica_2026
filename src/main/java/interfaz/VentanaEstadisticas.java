package interfaz;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class VentanaEstadisticas extends JFrame {

    public VentanaEstadisticas(int dinero, int pedidosCorrectos, int cancelados) {

        setTitle("Resultados de la Partida");

        setSize(400, 300);

        setLayout(new GridLayout(3, 1));

        add(new JLabel("Dinero ganado: $" + dinero));
        add(new JLabel("Pedidos correctos: " + pedidosCorrectos));
        add(new JLabel("Pedidos cancelados: " + cancelados));

        setLocationRelativeTo(null);

        setVisible(true);

    }

}