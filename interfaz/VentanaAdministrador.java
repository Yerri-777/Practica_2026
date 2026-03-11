package interfaz;

import javax.swing.*;
import java.awt.*;

public class VentanaAdministrador extends JFrame {

    public VentanaAdministrador() {

        setTitle("Panel Administrador");
        setSize(700, 500);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Gestión de Productos");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        add(label, BorderLayout.NORTH);

        setVisible(true);
    }
}