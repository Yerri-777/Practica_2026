package interfaz;

import javax.swing.*;
import java.io.FileWriter;
import modelo.Partida;

public class VentanaReportes extends JFrame {

    public VentanaReportes(){

        setTitle("Exportar Reporte CSV");
        setSize(400,200);
        setLocationRelativeTo(null);

        JButton btnExportar = new JButton("Exportar Reporte");

        btnExportar.addActionListener(e->{
            exportarCSV();
        });

        add(btnExportar);

        setVisible(true);
    }

    private void exportarCSV(){

        try{

            FileWriter writer = new FileWriter("reporte_partidas.csv");

            writer.append("Jugador,Puntaje,Nivel\n");

            writer.flush();
            writer.close();

            JOptionPane.showMessageDialog(this,"Reporte generado");

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}