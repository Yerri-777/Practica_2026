package interfaz;

import acceso_datos.ProductoDAO;
import modelo.Producto;
import modelo.Sucursal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;

public class PanelProductos extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;
    private ProductoDAO productoDAO;
    private int idSucursal;

    public PanelProductos(int idSucursal) {

        this.idSucursal = idSucursal;
        productoDAO = new ProductoDAO();

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestión de Productos", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnActivar = new JButton("Activar / Desactivar");
        JButton btnActualizar = new JButton("Actualizar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnActivar);
        panelBotones.add(btnActualizar);

        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarProducto());
        btnEditar.addActionListener(e -> editarProducto());
        btnActivar.addActionListener(e -> cambiarEstado());
        btnActualizar.addActionListener(e -> cargarProductos());

        cargarProductos();
    }

    private void cargarProductos() {

        try {

            modelo.setRowCount(0);

            Producto[] productos = productoDAO.obtenerPorSucursal(idSucursal);

            for (Producto p : productos) {

                modelo.addRow(new Object[]{
                        p.getIdProducto(),
                        p.getNombre(),
                        p.getPrecio(),
                        p.isActivo() ? "Activo" : "Inactivo"
                });

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void agregarProducto() {

        JTextField nombre = new JTextField();
        JTextField precio = new JTextField();

        Object[] campos = {
                "Nombre:", nombre,
                "Precio:", precio
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Nuevo Producto", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {

            try {

                Producto p = new Producto();

                p.setNombre(nombre.getText());
                p.setPrecio(new BigDecimal(precio.getText()));
                p.setActivo(true);

                Sucursal s = new Sucursal();
                s.setIdSucursal(idSucursal);

                p.setSucursal(s);

                productoDAO.guardar(p);

                cargarProductos();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void editarProducto() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        JTextField nombre = new JTextField(modelo.getValueAt(fila, 1).toString());
        JTextField precio = new JTextField(modelo.getValueAt(fila, 2).toString());

        Object[] campos = {
                "Nombre:", nombre,
                "Precio:", precio
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Editar Producto", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {

            try {

                Producto p = new Producto();

                p.setIdProducto(id);
                p.setNombre(nombre.getText());
                p.setPrecio(new BigDecimal(precio.getText()));

                productoDAO.actualizar(p);

                cargarProductos();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void cambiarEstado() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        try {

            productoDAO.cambiarEstado(id);

            cargarProductos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}