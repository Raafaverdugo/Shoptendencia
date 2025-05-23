package controlador;

import modelo.Usuario;
import util.ConexionBD;

import java.sql.*;

// Controlador para operaciones relacionadas con usuarios
public class UsuarioController implements IUsuarioController {

    // Método que permite iniciar sesión con nombre de usuario y contraseña
    public Usuario login(String nombreUsuario, String contraseña) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contraseña = ?";
        try (
                Connection conn = ConexionBD.obtenerConexion(); // Se obtiene la conexión a la base de datos
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, nombreUsuario); // Se asigna el nombre de usuario
            stmt.setString(2, contraseña);    // Se asigna la contraseña

            ResultSet rs = stmt.executeQuery(); // Se ejecuta la consulta

            // Si hay un resultado, se crea un objeto Usuario y se retorna
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
            e.printStackTrace(); // Se imprime la excepción en caso de error
        }

        return null; // Retorna null si no se encontró el usuario
    }

    // Verifica si el usuario tiene rol de administrador
    public boolean esAdmin(Usuario usuario) {
        return usuario != null && "admin".equalsIgnoreCase(usuario.getRol());
    }

    // Registra un nuevo usuario en la base de datos
    public Usuario registrarUsuario(Usuario nuevo) {
        String sql = "INSERT INTO usuarios (nombre_usuario, contraseña, rol, nombre, email, telefono) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            // Debug opcional para revisar el estado del autocommit
            System.out.println("🔍 ¿Autocommit activo?: " + conn.getAutoCommit());

            // Se asignan los valores del nuevo usuario al statement
            stmt.setString(1, nuevo.getNombreUsuario());
            stmt.setString(2, nuevo.getContraseña());
            stmt.setString(3, nuevo.getRol());
            stmt.setString(4, nuevo.getNombre());
            stmt.setString(5, nuevo.getEmail());
            stmt.setString(6, nuevo.getTelefono());

            int filas = stmt.executeUpdate(); // Ejecuta la inserción

            // Si se insertó correctamente, se recupera la clave generada
            if (filas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1); // Se obtiene el ID generado
                    nuevo.setIdUsuario(idGenerado); // Se asigna al objeto usuario
                    System.out.println("✅ Usuario insertado con ID: " + idGenerado);
                    return nuevo; // Se retorna el usuario actualizado
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la excepción si ocurre un error
        }

        return null; // Retorna null si no se pudo registrar el usuario
    }
}
