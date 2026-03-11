package las_pipshas;

import interfaz.VentanaLogin;

import javax.swing.*;

public class Principal {

    public static void main(String[] args) {

        configurarLookAndFeel();

        // Iniciar directamente la ventana
        new VentanaLogin();
    }

    private static void configurarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo aplicar el LookAndFeel del sistema.");
        }
    }
}