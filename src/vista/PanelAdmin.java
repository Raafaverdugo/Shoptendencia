package vista;

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

    public PanelAdmin(Usuario admin) {
        this.admin = admin;
        setTitle("Panel de Administración");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Paleta de colores (azul claro para admin)
        Color fondoAzulAdmin = new Color(0xE1F5FE);
        Color azulBotonAdmin = new Color(0x0288D1);
        Color azulHoverAdmin = new Color(0x0277BD);
        Color textoAzulOscuroAdmin = new Color(0x01579B);
        Color bordeSuaveAdmin = new Color(0xB3E5FC);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(fondoAzulAdmin);

        JLabel lblTitulo = new JLabel("Bienvenido, Administrador " + admin.getNombreUsuario(), JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(textoAzulOscuroAdmin);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 15, 15));
        panelBotones.setBackground(fondoAzulAdmin);

        JButton btnVerZapatillas = crearBoton("Ver Zapatillas", azulBotonAdmin, azulHoverAdmin, Color.WHITE);
        JButton btnEditarZapatillas = crearBoton("Editar Zapatillas", azulBotonAdmin, azulHoverAdmin, Color.WHITE);
        JButton btnGestionZapatillas = crearBoton("Añadir/Eliminar Zapatillas", azulBotonAdmin, azulHoverAdmin, Color.WHITE);
        JButton btnCerrarSesion = crearBoton("Cerrar Sesión", azulBotonAdmin, azulHoverAdmin, Color.WHITE);

        panelBotones.add(btnVerZapatillas);
        panelBotones.add(btnEditarZapatillas);
        panelBotones.add(btnGestionZapatillas);
        panelBotones.add(btnCerrarSesion);
        panel.add(panelBotones);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Marca", "Modelo", "Talla", "Precio", "Stock"}, 0);
        tablaZapatillas = new JTable(modeloTabla);
        scrollPaneTabla = new JScrollPane(tablaZapatillas);
        scrollPaneTabla.setVisible(false);
        scrollPaneTabla.setPreferredSize(new Dimension(750, 200));
        scrollPaneTabla.setBackground(bordeSuaveAdmin);
        panel.add(scrollPaneTabla);

        // Acción: ver zapatillas
        btnVerZapatillas.addActionListener(e -> {
            if (!scrollPaneTabla.isVisible()) {
                cargarZapatillas();
                scrollPaneTabla.setVisible(true);
                btnVerZapatillas.setText("Ocultar Zapatillas");
            } else {
                scrollPaneTabla.setVisible(false);
                btnVerZapatillas.setText("Ver Zapatillas");
            }
            revalidate();
        });

        // Acción: editar zapatillas (muestra tabla, clic para editar)
        btnEditarZapatillas.addActionListener(e -> {
            cargarZapatillas();
            scrollPaneTabla.setVisible(true);
            revalidate();
        });

        // Acción: cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new PantallaInicio().setVisible(true);
        });

        // Clic para editar
        tablaZapatillas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && tablaZapatillas.getSelectedRow() != -1) {
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

        // Botón aún sin implementar
        btnGestionZapatillas.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función para gestionar zapatillas (pendiente)"));

        add(panel);
    }

    private void cargarZapatillas() {
        modeloTabla.setRowCount(0);
        List<Zapatilla> lista = zapatillaController.obtenerTodas();
        for (Zapatilla z : lista) {
            modeloTabla.addRow(new Object[]{
                    z.getIdZapatilla(), z.getMarca(), z.getModelo(), z.getTalla(), z.getPrecio(), z.getStock()
            });
        }
    }

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
}
