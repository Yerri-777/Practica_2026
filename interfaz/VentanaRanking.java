package interfaz;

import javax.swing.*;


public class VentanaRanking extends JFrame {

    public VentanaRanking() {

        setTitle("Ranking General");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTable tabla = new JTable();
        add(new JScrollPane(tabla));

        setVisible(true);
    }
}