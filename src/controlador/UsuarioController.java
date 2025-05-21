package controlador;

import modelo.Usuario;
import util.ConexionBD;

import java.sql.*;

public class UsuarioController implements IUsuarioController {

    public Usuario login(String nombreUsuario, String contraseña) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contraseña = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setRol(rs.getString("rol"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefono(rs.getString("telefono"));
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean esAdmin(Usuario usuario) {
        return usuario != null && "admin".equalsIgnoreCase(usuario.getRol());
    }

    public Usuario registrarUsuario(Usuario nuevo) {
        String sql = "INSERT INTO usuarios (nombre_usuario, contraseña, rol, nombre, email, telefono) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nuevo.getNombreUsuario());
            stmt.setString(2, nuevo.getContraseña());
            stmt.setString(3, nuevo.getRol());
            stmt.setString(4, nuevo.getNombre());
            stmt.setString(5, nuevo.getEmail());
            stmt.setString(6, nuevo.getTelefono());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    nuevo.setIdUsuario(idGenerado);
                    return nuevo;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    private boolean existeUsuario(String nombreUsuario) {
        String sql = "SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreUsuario);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
