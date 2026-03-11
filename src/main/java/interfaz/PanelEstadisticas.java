package interfaz;

import acceso_datos.PartidaDAO;
import modelo.Partida;

import javax.swing.*;
import java.awt.*;

public class PanelEstadisticas extends JPanel {

    public PanelEstadisticas(int idSucursal) {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Estadísticas de la Sucursal", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new GridLayout(4, 1, 10, 10));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        try {

            PartidaDAO dao = new PartidaDAO();
            Partida[] partidas = dao.obtenerPorSucursal(idSucursal);

            int total = partidas.length;
            int puntajeTotal = 0;
            int mejor = 0;
            int peor = Integer.MAX_VALUE;

            for (Partida p : partidas) {

                int puntaje = p.getPuntajeFinal();

                puntajeTotal += puntaje;

                if (puntaje > mejor) {
                    mejor = puntaje;
                }

                if (puntaje < peor) {
                    peor = puntaje;
                }
            }

            int promedio = 0;

            if (total > 0) {
                promedio = puntajeTotal / total;
            } else {
                peor = 0;
            }

            panelDatos.add(crearLabel(" Total de partidas jugadas: " + total));
            panelDatos.add(crearLabel(" Puntaje promedio: " + promedio));
            panelDatos.add(crearLabel(" Mejor puntaje alcanzado: " + mejor));
            panelDatos.add(crearLabel(" Peor puntaje registrado: " + peor));

        } catch (Exception e) {

            e.printStackTrace();
        }

        add(panelDatos, BorderLayout.CENTER);
    }

    private JLabel crearLabel(String texto) {

        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 16));

        return label;
    }
}
