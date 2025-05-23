package vista;

import controlador.UsuarioController;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaInicio extends JFrame {
    // Campos para el formulario de login/registro
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtTelefono;

    // Controlador para manejar lógica de usuarios
    private UsuarioController usuarioController = new UsuarioController();

    public PantallaInicio() {
        // Configuración básica de la ventana
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../resources/Logo.jpg")));
        setTitle("ShopTendencia - Inicio");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Definición de colores personalizados
        Color azulCielo = new Color(135, 206, 235);
        Color azulClaro = new Color(173, 216, 230);

        // Panel principal con layout flexible
        JPanel panel = new JPanel();
        panel.setBackground(azulCielo);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título de bienvenida
        JLabel lblTitulo = new JLabel("Bienvenido a ShopTendencia");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        // Campo: Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblUsuario, gbc);

        txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtUsuario, gbc);

        // Campo: Contraseña
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblContraseña, gbc);

        txtContraseña = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtContraseña, gbc);

        // Campo: Nombre (para registro)
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblNombre, gbc);

        txtNombre = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(txtNombre, gbc);

        // Campo: Email (para registro)
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblEmail, gbc);

        txtEmail = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(txtEmail, gbc);

        // Campo: Teléfono (para registro)
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(lblTelefono, gbc);

        txtTelefono = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(txtTelefono, gbc);

        // Botón: Iniciar sesión
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(azulClaro);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(btnLogin, gbc);

        // Botón: Registrarse
        JButton btnRegistro = new JButton("Registrarse");
        btnRegistro.setBackground(azulClaro);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(btnRegistro, gbc);

        add(panel); // Se agrega el panel a la ventana

        // Acción del botón "Iniciar Sesión"
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contraseña = new String(txtContraseña.getPassword());

                Usuario u = usuarioController.login(usuario, contraseña);

                if (u != null) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                    dispose(); // Cierra esta ventana

                    // Abre panel correspondiente según rol
                    if (usuarioController.esAdmin(u)) {
                        new PanelAdmin(u).setVisible(true);
                    } else {
                        new PanelCliente(u).setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción del botón "Registrarse"
        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contraseña = new String(txtContraseña.getPassword());

                // Validación básica
                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Rellene los campos de usuario y contraseña", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String nombre = txtNombre.getText();
                String email = txtEmail.getText();
                String telefono = txtTelefono.getText();

                // Crea un nuevo usuario con rol "cliente"
                Usuario nuevo = new Usuario(0, usuario, contraseña, "cliente", nombre, email, telefono);
                Usuario registrado = usuarioController.registrarUsuario(nuevo);

                if (registrado != null) {
                    JOptionPane.showMessageDialog(null, "Usuario registrado correctamente con ID: " + registrado.getIdUsuario());
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
