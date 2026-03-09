package interfaz;

import javax.swing.*;


public class VentanaReportes extends JFrame {

    public VentanaReportes() {

        setTitle("Reportes");
        setSize(700, 500);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        add(new JScrollPane(area));

        setVisible(true);
    }
}