package interfaz;

import modelo.Usuario;
import modelo.Sucursal;
import modelo.Partida;

import acceso_datos.UsuarioDAO;
import acceso_datos.SucursalDAO;
import acceso_datos.PartidaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.ConfiguracionJuego;

public class VentanaSuperAdministrador extends JFrame {

    private JTable tabla;
    private Usuario superAdmin;

    public VentanaSuperAdministrador(Usuario superAdmin) {

        this.superAdmin = superAdmin;

        setTitle("SUPER ADMINISTRADOR");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panelBotones = new JPanel();

        JButton btnSucursales = new JButton("Gestionar Sucursales");
        JButton btnUsuarios = new JButton("Registrar Usuarios");
        JButton btnAsignar = new JButton("Asignar Usuario a Sucursal");
        JButton btnRanking = new JButton("Ranking Global");
        JButton btnEstadisticas = new JButton("Estadísticas Globales");
        JButton btnParametros = new JButton("Parámetros del Juego");
        JButton btnDashboard = new JButton("Global");

        panelBotones.add(btnSucursales);
        panelBotones.add(btnUsuarios);
        panelBotones.add(btnAsignar);
        panelBotones.add(btnRanking);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnParametros);
        panelBotones.add(btnDashboard);
        tabla = new JTable();

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnSucursales.addActionListener(e -> cargarSucursales());
        btnUsuarios.addActionListener(e -> registrarUsuario());
        btnAsignar.addActionListener(e -> asignarUsuarioSucursal());
        btnRanking.addActionListener(e -> rankingGlobal());
        btnEstadisticas.addActionListener(e -> estadisticasGlobales());
        btnParametros.addActionListener(e -> parametrosJuego());

        setVisible(true);
    }

   //sucursales
    private void cargarSucursales() {

        try {

            SucursalDAO dao = new SucursalDAO();
            Sucursal[] sucursales = dao.obtenerTodas();

            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("ID");
            modelo.addColumn("Nombre");
            modelo.addColumn("Dirección");

            for (Sucursal s : sucursales) {

                modelo.addRow(new Object[]{
                    s.getIdSucursal(),
                    s.getNombre(),
                    s.getDireccion()
                });

            }

            tabla.setModel(modelo);

            JPanel panelOpciones = new JPanel();

            JButton btnCrear = new JButton("Crear Sucursal");
            JButton btnEditar = new JButton("Editar Sucursal");
            JButton btnRefrescar = new JButton("Actualizar");

            panelOpciones.add(btnCrear);
            panelOpciones.add(btnEditar);
            panelOpciones.add(btnRefrescar);

            add(panelOpciones, BorderLayout.SOUTH);
            revalidate();

            btnCrear.addActionListener(e -> crearSucursal());
            btnEditar.addActionListener(e -> editarSucursal());
            btnRefrescar.addActionListener(e -> cargarSucursales());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void crearSucursal() {

        try {

            JTextField nombre = new JTextField();
            JTextField direccion = new JTextField();

            Object[] campos = {
                "Nombre:", nombre,
                "Dirección:", direccion
            };

            int opcion = JOptionPane.showConfirmDialog(this, campos,
                    "Nueva Sucursal",
                    JOptionPane.OK_CANCEL_OPTION);

            if (opcion == JOptionPane.OK_OPTION) {

                Sucursal s = new Sucursal();

                s.setNombre(nombre.getText());
                s.setDireccion(direccion.getText());

                SucursalDAO dao = new SucursalDAO();

                dao.guardar(s);

                JOptionPane.showMessageDialog(this, "Sucursal creada correctamente");

                cargarSucursales();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    //registro de usuario
    private void registrarUsuario() {

        try {

            JTextField txtUsuario = new JTextField();
            JPasswordField txtPassword = new JPasswordField();

            String[] roles = {"JUGADOR", "ADMIN_TIENDA"};
            JComboBox<String> rolCombo = new JComboBox<>(roles);

            Object[] campos = {
                "Usuario:", txtUsuario,
                "Password:", txtPassword,
                "Rol:", rolCombo
            };

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    campos,
                    "Registrar Usuario",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (opcion == JOptionPane.OK_OPTION) {

                String username = txtUsuario.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {

                    JOptionPane.showMessageDialog(this,
                            "Debe completar todos los campos");
                    return;
                }

                UsuarioDAO dao = new UsuarioDAO();

                if (dao.existeUsuario(username)) {

                    JOptionPane.showMessageDialog(this,
                            "El usuario ya existe.");
                    return;
                }

                Usuario u = new Usuario();
                u.setUsername(username);
                u.setPassword(password);

                String rolSeleccionado = rolCombo.getSelectedItem().toString();

                modelo.Rol rol = new modelo.Rol();

                switch (rolSeleccionado) {

                    case "JUGADOR":

                        rol.setIdRol(3); // ← CORRECTO
                        rol.setNombre("JUGADOR");
                        break;

                    case "ADMIN_TIENDA":

                        rol.setIdRol(2); // ← CORRECTO
                        rol.setNombre("ADMIN_TIENDA");
                        break;

                    default:

                        JOptionPane.showMessageDialog(this,
                                "Rol inválido");
                        return;
                }

                u.setRol(rol);

                dao.guardar(u);

                JOptionPane.showMessageDialog(this,
                        "Usuario registrado correctamente con rol: "
                        + rol.getNombre());

            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Error al registrar usuario");
        }
    }
   //asignar sucursal
    private void asignarUsuarioSucursal() {

        try {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            SucursalDAO sucursalDAO = new SucursalDAO();

            Usuario[] usuarios = usuarioDAO.obtenerTodos();
            Sucursal[] sucursales = sucursalDAO.obtenerTodas();

            // Validar que existan usuarios
            if (usuarios == null || usuarios.length == 0) {

                JOptionPane.showMessageDialog(this,
                        "No hay usuarios registrados");

                return;
            }

            // Validar que existan sucursales
            if (sucursales == null || sucursales.length == 0) {

                JOptionPane.showMessageDialog(this,
                        "No hay sucursales registradas");

                return;
            }

            JComboBox<Usuario> comboUsuarios = new JComboBox<>(usuarios);
            JComboBox<Sucursal> comboSucursales = new JComboBox<>(sucursales);

            Object[] campos = {
                "Usuario:", comboUsuarios,
                "Sucursal:", comboSucursales
            };

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    campos,
                    "Asignar Usuario a Sucursal",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (opcion == JOptionPane.OK_OPTION) {

                Usuario usuarioSeleccionado = (Usuario) comboUsuarios.getSelectedItem();
                Sucursal sucursalSeleccionada = (Sucursal) comboSucursales.getSelectedItem();

                // Validación extra de seguridad
                if (usuarioSeleccionado == null || sucursalSeleccionada == null) {

                    JOptionPane.showMessageDialog(this,
                            "Debe seleccionar un usuario y una sucursal");

                    return;
                }

                usuarioDAO.asignarSucursal(
                        usuarioSeleccionado.getIdUsuario(),
                        sucursalSeleccionada.getIdSucursal()
                );

                JOptionPane.showMessageDialog(this,
                        "Usuario asignado correctamente a la sucursal");

            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Error al asignar usuario a sucursal");

        }
    }

     private void editarSucursal() {

        try {

            int fila = tabla.getSelectedRow();

            if (fila == -1) {

                JOptionPane.showMessageDialog(this, "Seleccione una sucursal");
                return;
            }

            int id = (int) tabla.getValueAt(fila, 0);
            String nombreActual = (String) tabla.getValueAt(fila, 1);
            String direccionActual = (String) tabla.getValueAt(fila, 2);

            JTextField nombre = new JTextField(nombreActual);
            JTextField direccion = new JTextField(direccionActual);

            Object[] campos = {
                "Nombre:", nombre,
                "Dirección:", direccion
            };

            int opcion = JOptionPane.showConfirmDialog(this,
                    campos,
                    "Editar Sucursal",
                    JOptionPane.OK_CANCEL_OPTION);

            if (opcion == JOptionPane.OK_OPTION) {

                Sucursal s = new Sucursal();

                s.setIdSucursal(id);
                s.setNombre(nombre.getText());
                s.setDireccion(direccion.getText());

                SucursalDAO dao = new SucursalDAO();

                dao.actualizar(s);

                JOptionPane.showMessageDialog(this, "Sucursal actualizada");

                cargarSucursales();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
    
   //Ranking Global
    private void rankingGlobal() {

        try {

            UsuarioDAO dao = new UsuarioDAO();
            Usuario[] ranking = dao.rankingGlobal();

            if (ranking == null || ranking.length == 0) {

                JOptionPane.showMessageDialog(this,
                        "No hay jugadores registrados todavía.");
                return;
            }

            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("Posición");
            modelo.addColumn("Jugador");
            modelo.addColumn("Mejor Puntaje");

            int posicion = 1;

            for (Usuario u : ranking) {

                modelo.addRow(new Object[]{
                    posicion++,
                    u.getUsername(),
                    u.getMejorPuntaje()
                });

            }

            tabla.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Error al cargar ranking global");
            e.printStackTrace();
        }
    }

    //estadisticas Globales
    private void estadisticasGlobales() {

        try {

            PartidaDAO dao = new PartidaDAO();

            Partida[] partidas = dao.obtenerTodas();

            if (partidas == null || partidas.length == 0) {

                JOptionPane.showMessageDialog(this,
                        "No hay partidas registradas.");
                return;
            }

            int total = partidas.length;

            int suma = 0;
            int mayor = Integer.MIN_VALUE;

            for (Partida p : partidas) {

                int puntaje = p.getPuntajeFinal();

                suma += puntaje;

                if (puntaje > mayor) {
                    mayor = puntaje;
                }

            }

            int promedio = suma / total;

            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("Estadística");
            modelo.addColumn("Valor");

            modelo.addRow(new Object[]{"Total de partidas", total});
            modelo.addRow(new Object[]{"Puntaje promedio", promedio});
            modelo.addRow(new Object[]{"Mejor puntaje global", mayor});

            tabla.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Error al calcular estadísticas globales");

            e.printStackTrace();
        }
    }

  //parametros del juego osea tiempo de pedidos
    private void parametrosJuego() {

        JTextField tiempoPedido = new JTextField("30");

        Object[] campos = {
            "Tiempo base de pedidos (segundos):", tiempoPedido
        };

        int opcion = JOptionPane.showConfirmDialog(
                this,
                campos,
                "Configuración del Juego",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opcion == JOptionPane.OK_OPTION) {

            try {

                int tiempo = Integer.parseInt(tiempoPedido.getText());

                if (tiempo <= 0) {

                    JOptionPane.showMessageDialog(this,
                            "El tiempo debe ser mayor que 0");
                    return;
                }

                if (tiempo > 300) {

                    JOptionPane.showMessageDialog(this,
                            "Tiempo demasiado alto (máximo 300 segundos)");
                    return;
                }

                // Guardar en configuración del sistema
                ConfiguracionJuego.TIEMPO_PEDIDO = tiempo;

                JOptionPane.showMessageDialog(this,
                        "Parámetro actualizado correctamente");

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(this,
                        "Ingrese un número válido");

            }
        }
    }
}
