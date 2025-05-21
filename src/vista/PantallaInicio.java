package vista;

import controlador.UsuarioController;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaInicio extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContraseña;
    private UsuarioController usuarioController = new UsuarioController();

    public PantallaInicio() {
        setTitle("ShopTendencia - Inicio");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color azulCielo = new Color(135, 206, 235);
        Color azulClaro = new Color(173, 216, 230);

        JPanel panel = new JPanel();
        panel.setBackground(azulCielo);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitulo = new JLabel("Bienvenido a Shop Tendencia");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblUsuario, gbc);

        txtUsuario = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtUsuario, gbc);

        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblContraseña, gbc);

        txtContraseña = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(txtContraseña, gbc);

        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(azulClaro);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(btnLogin, gbc);

        JButton btnRegistro = new JButton("Registrarse");
        btnRegistro.setBackground(azulClaro);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(btnRegistro, gbc);

        add(panel);

        /* Acción de login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contraseña = new String(txtContraseña.getPassword());

                Usuario u = usuarioController.login(usuario, contraseña);
                if (u != null) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                    dispose();
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
*/
        // Acción de registro
        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contraseña = new String(txtContraseña.getPassword());

                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Rellene todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Usuario nuevo = new Usuario(0, usuario, contraseña, "cliente");
                boolean registrado = usuarioController.registrarCliente(nuevo);

                if (registrado) {
                    JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaInicio().setVisible(true));
    }
}
