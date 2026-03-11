package interfaz;

import acceso_datos.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelRanking extends JPanel {

    private int idSucursal;
    private JTable tabla;

    public PanelRanking(int idSucursal) {

        this.idSucursal = idSucursal;

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Ranking de Jugadores", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        add(titulo, BorderLayout.NORTH);

        tabla = new JTable();
        tabla.setRowHeight(28);

        cargarRanking();

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void cargarRanking() {

        try {

            UsuarioDAO dao = new UsuarioDAO();

            Usuario[] ranking = dao.rankingSucursal(idSucursal);

            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("#");
            modelo.addColumn("Jugador");
            modelo.addColumn("Mejor Puntaje");

            int posicion = 1;

            for (Usuario u : ranking) {

                modelo.addRow(new Object[]{
                        posicion,
                        u.getUsername(),
                        u.getMejorPuntaje()
                });

                posicion++;
            }

            tabla.setModel(modelo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}