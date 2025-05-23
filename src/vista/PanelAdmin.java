package vista;

import controlador.CompraController;
import controlador.ZapatillaController;
import modelo.Usuario;
import modelo.Zapatilla;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PanelAdmin extends JFrame {
    private Usuario admin;
    private JTable tablaZapatillas;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPaneTabla;
    private ZapatillaController zapatillaController = new ZapatillaController();
    private CompraController compraController = new CompraController();
    private JLabel lblMensaje;
    private boolean modoEdicion = false;

    public PanelAdmin(Usuario admin) {
        this.admin = admin;
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../resources/Logo.jpg")));
        setTitle("Panel de Administración");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Colores de diseño
        Color fondoAzulAdmin = new Color(0xE1F5FE);
        Color azulBotonAdmin = new Color(0x0288D1);
        Color azulHoverAdmin = new Color(0x0277BD);
        Color textoAzulOscuroAdmin = new Color(0x01579B);
        Color bordeSuaveAdmin = new Color(0xB3E5FC);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(fondoAzulAdmin);

        // Título de bienvenida
        JLabel lblTitulo = new JLabel("Bienvenido, Administrador " + admin.getNombreUsuario(), JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(textoAzulOscuroAdmin);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 15, 15));
        panelBotones.setBackground(fondoAzulAdmin);

        // Botones de acciones
        JButton btnVerZapatillas = crearBoton("Ver Zapatillas", azulBotonAdmin, azulHoverAdmin, Color.WHITE);
        JButton btnEditarZapatillas = crearBoton("Editar Zapatillas", azulBotonAdmin, azulHoverAdmin, Color.WHITE);
        JButton btnGestionZapatillas = crearBoton("Añadir/Eliminar Zapatillas", azulBotonAdmin, azulHoverAdmin, Color.WHITE);
        JButton btnCerrarSesion = crearBoton("Cerrar Sesión", azulBotonAdmin, azulHoverAdmin, Color.WHITE);
        JButton btnIngresosTotales = crearBoton("Ingresos Totales", azulBotonAdmin, azulHoverAdmin, Color.WHITE);

        // Agregamos todos los botones al panel
        panelBotones.add(btnVerZapatillas);
        panelBotones.add(btnEditarZapatillas);
        panelBotones.add(btnGestionZapatillas);
        panelBotones.add(btnCerrarSesion);
        panelBotones.add(btnIngresosTotales);
        panel.add(panelBotones);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tabla de zapatillas
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Marca", "Modelo", "Talla", "Precio", "Stock"}, 0);
        tablaZapatillas = new JTable(modeloTabla);
        scrollPaneTabla = new JScrollPane(tablaZapatillas);
        scrollPaneTabla.setVisible(false);
        scrollPaneTabla.setPreferredSize(new Dimension(750, 200));
        scrollPaneTabla.setBackground(bordeSuaveAdmin);
        panel.add(scrollPaneTabla);

        // Mensaje informativo
        lblMensaje = new JLabel("Haz clic para editar una zapatilla");
        lblMensaje.setFont(new Font("Arial", Font.ITALIC, 14));
        lblMensaje.setForeground(textoAzulOscuroAdmin);
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensaje.setVisible(false);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblMensaje);

        // Acción para ver zapatillas
        btnVerZapatillas.addActionListener(e -> {
            modoEdicion = false;
            lblMensaje.setVisible(false);
            if (!scrollPaneTabla.isVisible()) {
                cargarZapatillas();
                scrollPaneTabla.setVisible(true);
                btnVerZapatillas.setText("Ocultar Zapatillas");
                btnEditarZapatillas.setText("Editar Zapatillas");
            } else {
                scrollPaneTabla.setVisible(false);
                btnVerZapatillas.setText("Ver Zapatillas");
                btnEditarZapatillas.setText("Editar Zapatillas");
            }
            revalidate();
        });

        // Acción para editar zapatillas
        btnEditarZapatillas.addActionListener(e -> {
            if (!modoEdicion) {
                modoEdicion = true;
                cargarZapatillas();
                scrollPaneTabla.setVisible(true);
                lblMensaje.setText("Haz clic para editar una zapatilla");
                lblMensaje.setVisible(true);
                btnEditarZapatillas.setText("Ocultar Zapatillas");
                btnVerZapatillas.setText("Ver Zapatillas");
            } else {
                modoEdicion = false;
                scrollPaneTabla.setVisible(false);
                lblMensaje.setVisible(false);
                btnEditarZapatillas.setText("Editar Zapatillas");
                btnVerZapatillas.setText("Ver Zapatillas");
            }
            revalidate();
        });

        // Acción para cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new PantallaInicio().setVisible(true);
        });

        // Acción para mostrar ingresos totales
        btnIngresosTotales.addActionListener(e -> {
            double total = compraController.calcularIngresosTotales();
            JOptionPane.showMessageDialog(this,
                    "Los ingresos totales acumulados son: $" + String.format("%.2f", total),
                    "Ingresos Totales",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Acción de clic para editar en la tabla
        tablaZapatillas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (modoEdicion && e.getClickCount() == 1 && tablaZapatillas.getSelectedRow() != -1) {
                    int fila = tablaZapatillas.getSelectedRow();
                    int idZapatilla = (int) modeloTabla.getValueAt(fila, 0);
                    Zapatilla z = zapatillaController.buscarPorId(idZapatilla);
                    if (z != null) {
                        mostrarDialogoEditar(z);
                    } else {
                        JOptionPane.showMessageDialog(PanelAdmin.this, "No se pudo cargar la zapatilla.");
                    }
                }
            }
        });

        // Acción para agregar o eliminar zapatillas
        btnGestionZapatillas.addActionListener(e -> {
            String[] opciones = {"Agregar Zapatilla", "Eliminar Zapatilla", "Cancelar"};
            int eleccion = JOptionPane.showOptionDialog(
                    this,
                    "¿Qué deseas hacer?",
                    "Gestión de Zapatillas",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (eleccion == 0) {
                mostrarDialogoAgregar();
            } else if (eleccion == 1) {
                mostrarDialogoEliminar();
            }
        });

        add(panel);
    }

    // Método para cargar las zapatillas en la tabla
    private void cargarZapatillas() {
        modeloTabla.setRowCount(0);
        List<Zapatilla> lista = zapatillaController.obtenerTodas();
        for (Zapatilla z : lista) {
            modeloTabla.addRow(new Object[]{
                    z.getIdZapatilla(), z.getMarca(), z.getModelo(), z.getTalla(), z.getPrecio(), z.getStock()
            });
        }
    }

    // Estilo de los botones
    private JButton crearBoton(String texto, Color colorFondo, Color colorHover, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(colorHover);
            }

            public void mouseExited(MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });

        return boton;
    }

    // Diálogo para editar zapatilla
    private void mostrarDialogoEditar(Zapatilla zapatilla) {
        JTextField txtMarca = new JTextField(zapatilla.getMarca());
        JTextField txtModelo = new JTextField(zapatilla.getModelo());
        JTextField txtTalla = new JTextField(String.valueOf(zapatilla.getTalla()));
        JTextField txtPrecio = new JTextField(String.valueOf(zapatilla.getPrecio()));
        JTextField txtStock = new JTextField(String.valueOf(zapatilla.getStock()));

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Marca:"));
        panel.add(txtMarca);
        panel.add(new JLabel("Modelo:"));
        panel.add(txtModelo);
        panel.add(new JLabel("Talla:"));
        panel.add(txtTalla);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Stock:"));
        panel.add(txtStock);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Zapatilla", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                zapatilla.setMarca(txtMarca.getText());
                zapatilla.setModelo(txtModelo.getText());
                zapatilla.setTalla(Float.parseFloat(txtTalla.getText()));
                zapatilla.setPrecio(Float.parseFloat(txtPrecio.getText()));
                zapatilla.setStock(Integer.parseInt(txtStock.getText()));

                if (zapatillaController.actualizarZapatilla(zapatilla)) {
                    JOptionPane.showMessageDialog(this, "Zapatilla actualizada correctamente.");
                    cargarZapatillas();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la zapatilla.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Verifica los datos numéricos.");
            }
        }
    }

    // Diálogo para agregar zapatilla
    private void mostrarDialogoAgregar() {
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtTalla = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Marca:"));
        panel.add(txtMarca);
        panel.add(new JLabel("Modelo:"));
        panel.add(txtModelo);
        panel.add(new JLabel("Talla:"));
        panel.add(txtTalla);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Stock:"));
        panel.add(txtStock);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Agregar Nueva Zapatilla", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                Zapatilla nueva = new Zapatilla();
                nueva.setMarca(txtMarca.getText());
                nueva.setModelo(txtModelo.getText());
                nueva.setTalla(Float.parseFloat(txtTalla.getText()));
                nueva.setPrecio(Float.parseFloat(txtPrecio.getText()));
                nueva.setStock(Integer.parseInt(txtStock.getText()));

                if (zapatillaController.agregarZapatilla(nueva)) {
                    JOptionPane.showMessageDialog(this, "Zapatilla agregada correctamente.");
                    cargarZapatillas();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar la zapatilla.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Verifica los datos numéricos.");
            }
        }
    }

    // Diálogo para eliminar zapatilla
    private void mostrarDialogoEliminar() {
        List<Zapatilla> lista = zapatillaController.obtenerTodas();

        String[] columnas = {"ID", "Marca", "Modelo", "Talla", "Precio", "Stock"};
        Object[][] datos = new Object[lista.size()][6];
        for (int i = 0; i < lista.size(); i++) {
            Zapatilla z = lista.get(i);
            datos[i][0] = z.getIdZapatilla();
            datos[i][1] = z.getMarca();
            datos[i][2] = z.getModelo();
            datos[i][3] = z.getTalla();
            datos[i][4] = z.getPrecio();
            datos[i][5] = z.getStock();
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setPreferredScrollableViewportSize(new Dimension(600, 200));

        int opcion = JOptionPane.showConfirmDialog(this, scrollPane, "Selecciona una zapatilla para eliminar", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION && tabla.getSelectedRow() != -1) {
            int filaSeleccionada = tabla.getSelectedRow();
            int id = (int) tabla.getValueAt(filaSeleccionada, 0);

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar la zapatilla con ID " + id + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                if (zapatillaController.eliminarZapatilla(id)) {
                    JOptionPane.showMessageDialog(this, "Zapatilla eliminada correctamente.");
                    cargarZapatillas();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar la zapatilla.");
                }
            }
        } else if (tabla.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una zapatilla para eliminar.");
        }
    }
}
