package vista;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAdmin extends JFrame {
    private Usuario admin;

    public PanelAdmin(Usuario admin) {
        this.admin = admin;
        setTitle("Panel de Administración");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Encabezado
        JLabel lblTitulo = new JLabel("Bienvenido, Administrador " + admin.getNombreUsuario(), JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Centro: botones de acciones administrativas
        JPanel centro = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton btnGestionUsuarios = new JButton("Editar Zapatillas");
        JButton btnGestionZapatillas = new JButton("Añadir/Eliminar Zapatillas");
        JButton btnVerCompras = new JButton("Ver Compras");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        centro.add(btnGestionUsuarios);
        centro.add(btnGestionZapatillas);
        centro.add(btnVerCompras);
        centro.add(btnCerrarSesion);

        panel.add(centro, BorderLayout.CENTER);

        // Acción: cerrar sesión
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // cerrar panel admin
                new PantallaInicio().setVisible(true); // volver al login
            }
        });

        // Aquí irían más acciones reales (por ejemplo, abrir nuevas ventanas)
        btnGestionUsuarios.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función para gestionar usuarios (pendiente)"));
        btnGestionZapatillas.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función para gestionar zapatillas (pendiente)"));
        btnVerCompras.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función para ver compras (pendiente)"));

        add(panel);
    }
}
