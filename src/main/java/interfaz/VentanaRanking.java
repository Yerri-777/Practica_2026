package interfaz;

import modelo.Usuario;
import modelo.Partida;
import acceso_datos.PartidaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaRanking extends JFrame {

    private JTable tabla;

    public VentanaRanking(Usuario usuario) {
        setTitle("Ranking Global de Jugadores");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tabla = new JTable();
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarRanking();
        
        
    }


    private void cargarRanking() {
        try {
            PartidaDAO dao = new PartidaDAO();
            Partida[] partidas = dao.obtenerTodas();

            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; 
                }
            };

            modelo.addColumn("ID Jugador");
            modelo.addColumn("Jugador");
            modelo.addColumn("Nivel");
            modelo.addColumn("Pedidos Correctos");
            modelo.addColumn("Cancelados");
            modelo.addColumn("Tarde");
            modelo.addColumn("Puntaje");
            modelo.addColumn("Tiempo Jugado");

            if (partidas != null) {
                for (Partida p : partidas) {
                    if (p != null && p.getUsuario() != null) {
                        int id = p.getUsuario().getIdUsuario();
                        String nombre = p.getUsuario().getUsername();
                        int nivel = p.getNivelAlcanzado();
                        int correctos = p.getPedidosCorrectos();
                        int cancelados = p.getPedidosCancelados();
                        int tarde = p.getPedidosTarde();
                        int puntaje = p.getPuntajeFinal();

                        String tiempo = "N/A";
                        if (p.getFechaInicio() != null && p.getFechaFin() != null) {
                            long segundos = java.time.Duration
                                    .between(p.getFechaInicio(), p.getFechaFin())
                                    .getSeconds();
                            tiempo = segundos + " s";
                        }

                        modelo.addRow(new Object[]{
                            id, nombre, nivel, correctos, cancelados, tarde, puntaje, tiempo
                        });
                    }
                }
            }

            tabla.setModel(modelo);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar ranking");
        }
    }
}
