package controlador;

import modelo.Usuario;
import util.ConexionBD;

import java.sql.*;

public class UsuarioController implements IUsuarioController {

    public Usuario login(String nombreUsuario, String contraseña) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contraseña = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_usuario");
                String rol = rs.getString("rol");
                usuario = new Usuario(id, nombreUsuario, contraseña, rol);
                System.out.println("✅ Login correcto: " + usuario);
            } else {
                System.out.println("❌ Usuario o contraseña incorrectos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public boolean esAdmin(Usuario usuario) {
        return usuario != null && "admin".equalsIgnoreCase(usuario.getRol());
    }

    public boolean registrarCliente(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre_usuario, contraseña, rol) VALUES (?, ?, 'cliente')";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getContraseña());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
