package vista;

// Importación de controladores y modelos necesarios
import controlador.CompraController;
import controlador.ZapatillaController;
import modelo.Compra;
import modelo.Usuario;
import modelo.Zapatilla;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.List;

// Clase principal del panel del cliente que extiende JFrame para crear una interfaz gráfica
public class PanelCliente extends JFrame {
    // Atributos
    private Usuario cliente;
    private JTable tablaZapatillas;
    private DefaultTableModel modeloTabla;
    private ZapatillaController zapatillaController = new ZapatillaController();
    private CompraController compraController = new CompraController();
    private JScrollPane scrollPaneTabla;
    private JLabel lblMensajeCompra;

    // Constructor principal que recibe el usuario cliente
    public PanelCliente(Usuario cliente) {
        this.cliente = cliente;

        // Configuración básica de la ventana
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../resources/Logo.jpg")));
        setTitle("Shoptendencia");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Definición de colores personalizados
        Color fondoAzulCielo = new Color(0xE3F2FD);
        Color azulBoton = new Color(0x64B5F6);
        Color azulHover = new Color(0x42A5F5);
        Color textoAzulOscuro = new Color(0x0D47A1);
        Color bordeSuave = new Color(0xBBDEFB);

        // Panel principal de la interfaz
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(fondoAzulCielo);

        // Etiqueta de bienvenida
        JLabel lblTitulo = new JLabel("Bienvenido, " + cliente.getNombreUsuario(), JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(textoAzulOscuro);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel con botones funcionales
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 15, 15));
        panelBotones.setBackground(fondoAzulCielo);

        // Creación de botones con estilos
        JButton btnVerZapatillas = crearBoton("Ver Zapatillas", azulBoton, azulHover, Color.WHITE);
        JButton btnMisCompras = crearBoton("Mis Compras", azulBoton, azulHover, Color.WHITE);
        JButton btnMiCuenta = crearBoton("Mi Cuenta", azulBoton, azulHover, Color.WHITE);
        JButton btnCerrarSesion = crearBoton("Cerrar Sesión", azulBoton, azulHover, Color.WHITE);

        // Agregando botones al panel
        panelBotones.add(btnVerZapatillas);
        panelBotones.add(btnMisCompras);
        panelBotones.add(btnMiCuenta);
        panelBotones.add(btnCerrarSesion);
        panel.add(panelBotones);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tabla y scroll para mostrar zapatillas
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Marca", "Modelo", "Talla", "Precio", "Stock"}, 0);
        tablaZapatillas = new JTable(modeloTabla);
        scrollPaneTabla = new JScrollPane(tablaZapatillas);
        scrollPaneTabla.setVisible(false); // oculto por defecto
        scrollPaneTabla.setPreferredSize(new Dimension(750, 200));
        scrollPaneTabla.setBackground(bordeSuave);
        panel.add(scrollPaneTabla);

        // Etiqueta informativa para compras
        lblMensajeCompra = new JLabel("Haz clic en una zapatilla para comprar");
        lblMensajeCompra.setForeground(textoAzulOscuro);
        lblMensajeCompra.setFont(new Font("Arial", Font.ITALIC, 14));
        lblMensajeCompra.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMensajeCompra.setVisible(false);
        panel.add(lblMensajeCompra);

        // Acción para cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new PantallaInicio().setVisible(true); // Vuelve a la pantalla de inicio
        });

        // Acción para ver/ocultar zapatillas
        btnVerZapatillas.addActionListener(e -> {
            if (!scrollPaneTabla.isVisible()) {
                cargarZapatillas();
                scrollPaneTabla.setVisible(true);
                lblMensajeCompra.setVisible(true);
                btnVerZapatillas.setText("Ocultar Zapatillas");
            } else {
                scrollPaneTabla.setVisible(false);
                lblMensajeCompra.setVisible(false);
                btnVerZapatillas.setText("Ver Zapatillas");
            }
            revalidate();
        });

        // Acción para mostrar el historial de compras del usuario
        btnMisCompras.addActionListener(e -> {
            List<Compra> compras = compraController.obtenerComprasPorCliente(cliente.getIdUsuario());

            if (compras.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No tienes compras registradas.");
                return;
            }

            // Construcción de tabla con los datos de compras
            String[] columnas = {"Modelo", "Fecha", "Cantidad", "Total"};
            Object[][] datos = new Object[compras.size()][columnas.length];
            for (int i = 0; i < compras.size(); i++) {
                Compra c = compras.get(i);
                datos[i][0] = c.getModeloZapatilla();
                datos[i][1] = c.getFechaCompra();
                datos[i][2] = c.getCantidad();
                datos[i][3] = c.getTotal();
            }

            JTable tablaCompras = new JTable(datos, columnas);
            JScrollPane scrollPane = new JScrollPane(tablaCompras);
            scrollPane.setPreferredSize(new Dimension(600, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Historial de Compras", JOptionPane.INFORMATION_MESSAGE);
        });

        // Acción para mostrar datos del usuario
        btnMiCuenta.addActionListener(e -> {
            String infoUsuario = "Nombre de usuario: " + cliente.getNombreUsuario() + "\n" +
                    "Nombre: " + cliente.getNombre() + "\n" +
                    "Email: " + cliente.getEmail() + "\n" +
                    "Teléfono: " + cliente.getTelefono() + "\n" +
                    "Rol: " + cliente.getRol();

            JOptionPane.showMessageDialog(this, infoUsuario, "Datos del usuario", JOptionPane.INFORMATION_MESSAGE);
        });

        // Evento: clic en una fila de la tabla para registrar una compra
        tablaZapatillas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaZapatillas.rowAtPoint(e.getPoint());
                if (fila >= 0 && e.getClickCount() == 1) {
                    try {
                        // Obtener datos de la zapatilla seleccionada
                        int idZapatilla = (int) modeloTabla.getValueAt(fila, 0);
                        String marca = (String) modeloTabla.getValueAt(fila, 1);
                        String modelo = (String) modeloTabla.getValueAt(fila, 2);
                        float talla = Float.parseFloat(modeloTabla.getValueAt(fila, 3).toString());
                        float precio = Float.parseFloat(modeloTabla.getValueAt(fila, 4).toString());
                        int stock = Integer.parseInt(modeloTabla.getValueAt(fila, 5).toString());

                        // Solicitar cantidad de compra
                        String input = JOptionPane.showInputDialog(PanelCliente.this,
                                "¿Cuántas unidades de \"" + marca + " " + modelo + " (talla " + talla + ")\" deseas comprar?",
                                "Registrar Compra", JOptionPane.QUESTION_MESSAGE);

                        if (input == null || input.trim().isEmpty()) return;

                        int cantidad = Integer.parseInt(input.trim());

                        // Validaciones
                        if (cantidad <= 0) {
                            JOptionPane.showMessageDialog(PanelCliente.this, "La cantidad debe ser mayor que 0.");
                            return;
                        }
                        if (cantidad > stock) {
                            JOptionPane.showMessageDialog(PanelCliente.this, "No hay suficiente stock.");
                            return;
                        }

                        // Registrar la compra
                        double total = cantidad * precio;
                        Date fecha = new Date(System.currentTimeMillis());

                        if (compraController.registrarCompra(cliente.getIdUsuario(), idZapatilla, fecha, cantidad, total)) {
                            JOptionPane.showMessageDialog(PanelCliente.this, "✅ Compra registrada correctamente. Total: " + total + " €");
                            cargarZapatillas(); // Actualiza el stock
                        } else {
                            JOptionPane.showMessageDialog(PanelCliente.this, "❌ Error al registrar la compra.");
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(PanelCliente.this, "Por favor, introduce un número válido.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(PanelCliente.this, "Ocurrió un error inesperado.");
                    }
                }
            }
        });

        // Añadir panel a la ventana
        add(panel);
    }

    // Método auxiliar para cargar zapatillas en la tabla
    private void cargarZapatillas() {
        modeloTabla.setRowCount(0); // Limpia la tabla
        List<Zapatilla> lista = zapatillaController.obtenerTodas();
        for (Zapatilla z : lista) {
            modeloTabla.addRow(new Object[]{
                    z.getIdZapatilla(), z.getMarca(), z.getModelo(), z.getTalla(), z.getPrecio(), z.getStock()
            });
        }
    }

    // Método auxiliar para crear botones con estilos y eventos de hover
    private JButton crearBoton(String texto, Color colorFondo, Color colorHover, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Cambia color al pasar el ratón
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
}
