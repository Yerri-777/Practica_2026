package interfaz;

import javax.swing.*;
import java.awt.*;
import modelo.Usuario;
import modelo.Partida;
import acceso_datos.PartidaDAO;

public class VentanaAdministrador extends JFrame {

    private JPanel panelContenido;
    private Usuario admin;
    private int idSucursal;

    private JLabel lblInfoAdmin;

    public VentanaAdministrador(Usuario admin) {

        this.admin = admin;
        this.idSucursal = admin.getSucursal().getIdSucursal();

        setTitle("Panel Administrador | Sucursal: "
                + admin.getSucursal().getNombre()
                + " | Admin: "
                + admin.getUsername());

        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // INFO DEL ADMIN
        lblInfoAdmin = new JLabel(
                "Administrador: " + admin.getUsername()
                + "  |  Sucursal: " + admin.getSucursal().getNombre(),
                SwingConstants.CENTER
        );

        lblInfoAdmin.setFont(new Font("Arial", Font.BOLD, 14));
        lblInfoAdmin.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        add(lblInfoAdmin, BorderLayout.SOUTH);

        // botones arriba
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton btnProductos = new JButton("Gestionar Productos");
        JButton btnRanking = new JButton("Ranking Jugadores");
        JButton btnEstadisticas = new JButton("Estadísticas");
        JButton btnReportes = new JButton("Exportar CSV");

        panelBotones.add(btnProductos);
        panelBotones.add(btnRanking);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnReportes);

        add(panelBotones, BorderLayout.NORTH);

        // Panel central
        panelContenido = new JPanel(new BorderLayout());
        add(panelContenido, BorderLayout.CENTER);

        // eventos
        btnProductos.addActionListener(e -> mostrarProductos());
        btnRanking.addActionListener(e -> mostrarRanking());
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
        btnReportes.addActionListener(e -> exportarCSV());

        setVisible(true);
    }

    private void limpiarPanel() {

        panelContenido.removeAll();
        panelContenido.revalidate();
        panelContenido.repaint();

    }

    private void mostrarProductos() {

        limpiarPanel();
        panelContenido.add(new PanelProductos(idSucursal), BorderLayout.CENTER);

    }

    private void mostrarRanking() {

        limpiarPanel();
        panelContenido.add(new PanelRanking(idSucursal), BorderLayout.CENTER);

    }

    private void mostrarEstadisticas() {

        limpiarPanel();
        panelContenido.add(new PanelEstadisticas(idSucursal), BorderLayout.CENTER);

    }

    private void exportarCSV() {

        try {

            PartidaDAO dao = new PartidaDAO();

            Partida[] partidas = dao.obtenerPorSucursal(idSucursal);

            if (partidas == null || partidas.length == 0) {

                JOptionPane.showMessageDialog(this,
                        "No hay partidas registradas para esta sucursal");

                return;
            }

            JFileChooser chooser = new JFileChooser();

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

                java.io.File file = chooser.getSelectedFile();

                java.io.PrintWriter writer = new java.io.PrintWriter(file + ".csv");

                writer.println("Jugador,Puntaje,Nivel");

                for (Partida p : partidas) {

                    if (p != null && p.getUsuario() != null) {

                        writer.println(
                                p.getUsuario().getUsername() + ","
                                + p.getPuntajeFinal() + ","
                                + p.getNivelAlcanzado()
                        );

                    }

                }

                writer.close();

                JOptionPane.showMessageDialog(this,
                        "Reporte CSV generado correctamente");

            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Error al exportar CSV");

        }

    }

}