package interfaz;

import modelo.*;
import servicio.ServicioJuego;
import utilidades.GestorTemporizadores;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class VentanaJuego extends JFrame {

    private Usuario usuario;
    private JLabel lblPuntaje, lblNivel, lblJugador, lblDinero, lblPizzaActual, lblTiempoPedido;
    private JPanel panelPedidos, panelIngredientes;
    private JProgressBar barraTiempo;

    private ServicioJuego servicioJuego;
    private Partida partida;

    private GestorTemporizadores temporizadorGenerador;
    private GestorTemporizadores temporizadorBarra;

    private int nivelActual = 1;
    private int puntaje = 0;
    private int dinero = 100;

    private volatile boolean hiloTiempoCorriendo = false;
    private volatile boolean juegoCorriendo = true;

    private Pedido pedidoActual;
    private JLabel lblProgreso;

    private String[] ingredientesJugador = new String[10];
    private int cantidadIngredientesJugador = 0;

    public VentanaJuego(Usuario usuario) throws SQLException, ClassNotFoundException {

        this.usuario = usuario;
        this.servicioJuego = new ServicioJuego();

        setTitle("Pizza Express Tycoon - Juego");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel fondo = new JLabel();
        fondo.setLayout(new BorderLayout());

        URL imgURL = getClass().getResource("/fondo_juego.jpg");
        if (imgURL != null) {
            fondo.setIcon(new ImageIcon(imgURL));
        }

        add(fondo);

        JPanel panelSuperior = new JPanel(new GridLayout(1, 4));
        panelSuperior.setBackground(new Color(30, 30, 30));

        lblJugador = new JLabel("Jugador: " + usuario.getUsername(), SwingConstants.CENTER);
        lblJugador.setForeground(Color.WHITE);

        barraTiempo = new JProgressBar(0, 100);
        barraTiempo.setValue(100);

        lblNivel = new JLabel("Nivel: " + nivelActual, SwingConstants.CENTER);
        lblNivel.setForeground(Color.WHITE);

        lblProgreso = new JLabel("Progreso: 0 / 5 pedidos", SwingConstants.CENTER);
        lblProgreso.setForeground(Color.CYAN);
        panelSuperior.add(lblProgreso);

        lblPuntaje = new JLabel("Puntaje: " + puntaje, SwingConstants.CENTER);
        lblPuntaje.setForeground(Color.WHITE);

        lblDinero = new JLabel("Dinero: $" + dinero, SwingConstants.CENTER);
        lblDinero.setForeground(Color.YELLOW);

        panelSuperior.add(lblJugador);
        panelSuperior.add(lblNivel);
        panelSuperior.add(lblPuntaje);
        panelSuperior.add(lblDinero);

        fondo.add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new GridLayout(1, 2));
        panelCentro.setOpaque(false);

        panelPedidos = new JPanel(new FlowLayout());
        panelPedidos.setBorder(BorderFactory.createTitledBorder("Pedidos Activos"));
        panelCentro.add(new JScrollPane(panelPedidos));

        JPanel panelPreparacion = new JPanel(new BorderLayout());
        panelPreparacion.setBorder(BorderFactory.createTitledBorder("Preparar Pizza"));

        lblPizzaActual = new JLabel("Esperando cliente...", SwingConstants.CENTER);
        lblPizzaActual.setFont(new Font("Arial", Font.BOLD, 18));

        lblTiempoPedido = new JLabel("Ingredientes: []", SwingConstants.CENTER);

        panelIngredientes = new JPanel(new GridLayout(2, 2, 10, 10));

        panelIngredientes.add(crearBotonIngrediente("Tomate", "/tomate.jpg"));
        panelIngredientes.add(crearBotonIngrediente("Queso", "/queso.jpg"));
        panelIngredientes.add(crearBotonIngrediente("Pepperoni", "/peperoni.jpg"));
        panelIngredientes.add(crearBotonIngrediente("Champiñones", "/champinion.jpg"));

        panelPreparacion.add(lblPizzaActual, BorderLayout.NORTH);
        panelPreparacion.add(panelIngredientes, BorderLayout.CENTER);

        JPanel panelSurPrep = new JPanel(new GridLayout(2, 1));
        panelSurPrep.add(barraTiempo);
        panelSurPrep.add(lblTiempoPedido);

        panelPreparacion.add(panelSurPrep, BorderLayout.SOUTH);

        panelCentro.add(panelPreparacion);

        fondo.add(panelCentro, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();

        JButton btnCompletar = new JButton("Entregar Pedido");
        JButton btnVolverMenu = new JButton("Salir al menú");

        btnCompletar.addActionListener(e -> completarPedido());
        btnVolverMenu.addActionListener(e -> salirJuego());

        panelInferior.add(btnCompletar);
        panelInferior.add(btnVolverMenu);

        fondo.add(panelInferior, BorderLayout.SOUTH);

        iniciarJuego();

        setVisible(true);
    }

    private ImageIcon cargarImagen(String ruta, int ancho, int alto) {

        try {

            URL url = getClass().getResource(ruta);

            if (url == null) {
                return null;
            }

            Image img = new ImageIcon(url).getImage();
            Image imgEscalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

            return new ImageIcon(imgEscalada);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private JButton crearBotonIngrediente(String nombre, String ruta) {

        JButton boton = new JButton(nombre);

        ImageIcon icon = cargarImagen(ruta, 60, 60);

        if (icon != null) {
            boton.setIcon(icon);
        }

        boton.addActionListener(e -> {

            if (pedidoActual == null) {
                JOptionPane.showMessageDialog(this, "No hay pedido activo");
                return;
            }

            if (cantidadIngredientesJugador < ingredientesJugador.length) {

                ingredientesJugador[cantidadIngredientesJugador] = nombre;
                cantidadIngredientesJugador++;

            }

            actualizarTextoPantalla(0, barraTiempo.getValue());
        });

        return boton;
    }

    private String obtenerIngredientesPizzaTexto() {

        if (pedidoActual == null) {
            return "";
        }

        String[] ingredientes = pedidoActual.getPizza().getIngredientes();

        String texto = "";

        for (int i = 0; i < ingredientes.length; i++) {

            texto += ingredientes[i];

            if (i < ingredientes.length - 1) {
                texto += ", ";
            }
        }

        return texto;
    }

    private String obtenerIngredientesJugadorTexto() {

        String texto = "";

        for (int i = 0; i < cantidadIngredientesJugador; i++) {

            texto += ingredientesJugador[i];

            if (i < cantidadIngredientesJugador - 1) {
                texto += ", ";
            }
        }

        return texto;
    }

    private void iniciarJuego() throws SQLException, ClassNotFoundException {
        partida = servicioJuego.iniciarPartida(usuario);

        actualizarProgresoNivel();

        Thread hiloGenerador = new Thread(() -> {
            while (juegoCorriendo) {
                try {
                    // Esperar 8 segundos
                    Thread.sleep(8000);

                    // Si no hay pedido, generamos uno nuevo
                    if (pedidoActual == null && juegoCorriendo) {
                        try {
                            generarPedido();
                        } catch (Exception e) {
                            System.err.println("Error al generar pedido en el hilo: " + e.getMessage());
                        }
                    }
                } catch (InterruptedException e) {
                    // Si el hilo se interrumpe, salimos del bucle
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

// Se detiene si se cierra la aplicación
        hiloGenerador.setDaemon(true);
        hiloGenerador.start();
    }

    private void generarPedido() throws SQLException, ClassNotFoundException {

        pedidoActual = servicioJuego.generarPedido(partida, nivelActual);

        JLabel lblPedido = new JLabel("Pedido #" + pedidoActual.getIdPedido());

        lblPedido.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblPedido.setPreferredSize(new Dimension(140, 70));
        lblPedido.setHorizontalAlignment(SwingConstants.CENTER);

        panelPedidos.add(lblPedido);

        panelPedidos.revalidate();
        panelPedidos.repaint();

        lblPizzaActual.setText("Pizza: " + pedidoActual.getPizza().getNombre());

        mostrarImagenPizza(pedidoActual.getPizza().getImagen());

        String ingredientes = obtenerIngredientesPizzaTexto();

        lblTiempoPedido.setText("Ingredientes pedidos: " + ingredientes);
        int tiempo = pedidoActual.getTiempoLimite();

        if (nivelActual == 2) {
            tiempo = tiempo - 5;
        }

        if (nivelActual == 3) {
            tiempo = tiempo - 10;
        }

        if (tiempo < 5) {
            tiempo = 5;
        }

        iniciarTiempoCliente(tiempo);
    }

    private void iniciarTiempoCliente(int tiempoSegundos) {

        hiloTiempoCorriendo = false;

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }

        hiloTiempoCorriendo = true;

        barraTiempo.setValue(100);

        new Thread(() -> {

            int tiempoRestante = tiempoSegundos;

            try {

                while (tiempoRestante >= 0 && hiloTiempoCorriendo) {

                    int porcentajeBarra = (tiempoRestante * 100) / tiempoSegundos;

                    actualizarTextoPantalla(tiempoRestante, porcentajeBarra);

                    if (tiempoRestante == 0) {

                        hiloTiempoCorriendo = false;

                        procesarClientePerdido();

                        break;
                    }

                    Thread.sleep(1000);

                    tiempoRestante--;

                }

            } catch (InterruptedException e) {
            }

        }).start();
    }

    private void actualizarTextoPantalla(int segundos, int porcentaje) {

        String recetaObjetivo = obtenerIngredientesPizzaTexto();
        String ingredientesJugadorTxt = obtenerIngredientesJugadorTexto();

        barraTiempo.setValue(porcentaje);

        barraTiempo.setForeground(porcentaje < 30 ? Color.RED : Color.GREEN);

        lblTiempoPedido.setText(
                "" + segundos + "s | RECETA: " + recetaObjetivo + " | LLEVAS: " + ingredientesJugadorTxt
        );
    }

    private void procesarClientePerdido() {

        JOptionPane.showMessageDialog(this, "¡El cliente se cansó de esperar!");

        puntaje = Math.max(0, puntaje - 20);

        lblPuntaje.setText("Puntaje: " + puntaje);

        if (partida != null) {

            partida.setPedidosTarde(partida.getPedidosTarde() + 1);

        }

        limpiarPedido();

        verificarFinJuego();
    }

    private void completarPedido() {

        if (pedidoActual == null) {

            JOptionPane.showMessageDialog(this, "No hay pedido activo");

            return;

        }

        String receta = obtenerIngredientesPizzaTexto();
        String entrega = obtenerIngredientesJugadorTexto();
        if (entrega.equals(receta)) {

            JOptionPane.showMessageDialog(this, "¡Pedido correcto!");

            dinero += 40;

            sumarPuntos(30);

            partida.setPedidosCorrectos(partida.getPedidosCorrectos() + 1);

            actualizarProgresoNivel();

        } else {

            JOptionPane.showMessageDialog(this, "Pizza incorrecta");

            dinero = Math.max(0, dinero - 30);

            partida.setPedidosCancelados(partida.getPedidosCancelados() + 1);

        }

        lblDinero.setText("Dinero: $" + dinero);

        pedidoActual.setTiempoFinalizacion(LocalDateTime.now());

        limpiarPedido();

        hiloTiempoCorriendo = false;
    }

    private void limpiarPedido() {

        if (panelPedidos.getComponentCount() > 0) {

            panelPedidos.remove(0);

            panelPedidos.repaint();
            panelPedidos.revalidate();
        }

        pedidoActual = null;

        cantidadIngredientesJugador = 0;

        lblPizzaActual.setText("Esperando pedido...");

        lblPizzaActual.setIcon(null);

        lblTiempoPedido.setText("Ingredientes: ");

        barraTiempo.setValue(100);

        hiloTiempoCorriendo = false;
    }

    private void actualizarProgresoNivel() {

        int pedidos = partida.getPedidosCorrectos();

        if (nivelActual == 1) {

            lblProgreso.setText(
                    "Pedidos correctos: " + pedidos + " | Objetivo: 5 | Nivel actual: " + nivelActual
            );

        } else if (nivelActual == 2) {

            lblProgreso.setText(
                    "Pedidos correctos: " + pedidos + " | Objetivo: 10 | Nivel actual: " + nivelActual
            );

        } else {

            lblProgreso.setText("Nivel máximo alcanzado");

        }
    }

    private void sumarPuntos(int puntos) {

        puntaje += puntos;

        lblPuntaje.setText("Puntaje: " + puntaje);

        int pedidosCorrectos = partida.getPedidosCorrectos();

        int nuevoNivel = SistemaNiveles.calcularNivel(puntaje, pedidosCorrectos);

        if (nuevoNivel > nivelActual) {

            nivelActual = nuevoNivel;

            JOptionPane.showMessageDialog(this,
                    "¡Subiste al NIVEL " + nivelActual + "!");

            lblNivel.setText("Nivel: " + nivelActual);

            actualizarProgresoNivel();
        }

        verificarFinJuego();
    }

    private void mostrarImagenPizza(String ruta) {

        ImageIcon icon = cargarImagen(ruta, 120, 120);

        if (icon != null) {
            lblPizzaActual.setIcon(icon);
        } else {
            lblPizzaActual.setIcon(null);
        }
    }

    private void verificarFinJuego() {

        if (puntaje <= 0) {

            JOptionPane.showMessageDialog(this,
                    "PERDISTE\nTe quedaste sin puntos");

            salirJuego();
        }
    }

    private void salirJuego() {

        try {

            if (partida != null) {

                partida.setFechaFin(LocalDateTime.now());
                partida.setPuntajeFinal(puntaje);
                partida.setNivelAlcanzado(nivelActual);

                servicioJuego.finalizarPartida(partida);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        VentanaMenuPrincipal menu = new VentanaMenuPrincipal(usuario);

        menu.setVisible(true);

        dispose();

        juegoCorriendo = false;
    }
}
