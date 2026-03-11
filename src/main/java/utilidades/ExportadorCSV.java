package utilidades;

import modelo.Usuario;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportadorCSV {

    public static void exportarRanking(Usuario[] ranking) {
        // 1. Configurar el selector de archivos
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar Ranking");
        
       
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV", "csv");
        selector.setFileFilter(filtro);

      
        int resultado = selector.showSaveDialog(null);


        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            String ruta = archivo.getAbsolutePath();

     
            if (!ruta.toLowerCase().endsWith(".csv")) {
                archivo = new File(ruta + ".csv");
            }

         
            try (FileWriter writer = new FileWriter(archivo)) {
                
              
                writer.write("Jugador,Puntaje\n");

         
                for (Usuario u : ranking) {
                    if (u != null) {
                        // Concatenación limpia de los datos
                        String linea = u.getUsername() + "," + u.getMejorPuntaje() + "\n";
                        writer.write(linea);
                    }
                }
                
                System.out.println("Exportación exitosa.");

            } catch (IOException e) {
                System.err.println("Error al escribir: " + e.getMessage());
            }
        }
    }
}