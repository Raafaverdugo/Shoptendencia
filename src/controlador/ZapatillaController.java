package controlador;

import modelo.Zapatilla;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZapatillaController implements IZapatillaController {

    // Recupera todas las zapatillas almacenadas en la base de datos
    @Override
    public List<Zapatilla> obtenerTodas() {
        List<Zapatilla> lista = new ArrayList<>();
        String sql = "SELECT * FROM zapatillas";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            // Se recorren los resultados y se agregan a la lista
            while (rs.next()) {
                Zapatilla z = new Zapatilla(
                        rs.getInt("id_zapatilla"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getFloat("talla"),
                        rs.getFloat("precio"),
                        rs.getInt("stock")
                );
                lista.add(z);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista; // Retorna la lista de zapatillas
    }

    // Busca una zapatilla por su ID
    @Override
    public Zapatilla buscarPorId(int id) {
        Zapatilla z = null;
        String sql = "SELECT * FROM zapatillas WHERE id_zapatilla = ?";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // Si se encuentra, se construye el objeto Zapatilla
            if (rs.next()) {
                z = new Zapatilla(
                        rs.getInt("id_zapatilla"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getFloat("talla"),
                        rs.getFloat("precio"),
                        rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return z; // Puede ser null si no se encuentra
    }

    // Agrega una nueva zapatilla a la base de datos
    @Override
    public boolean agregarZapatilla(Zapatilla z) {
        String sql = "INSERT INTO zapatillas (marca, modelo, talla, precio, stock) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, z.getMarca());
            stmt.setString(2, z.getModelo());
            stmt.setFloat(3, z.getTalla());
            stmt.setDouble(4, z.getPrecio());
            stmt.setInt(5, z.getStock());

            return stmt.executeUpdate() > 0; // Retorna true si se insertó correctamente

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Actualiza los datos de una zapatilla existente
    @Override
    public boolean actualizarZapatilla(Zapatilla z) {
        String sql = "UPDATE zapatillas SET marca = ?, modelo = ?, talla = ?, precio = ?, stock = ? WHERE id_zapatilla = ?";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, z.getMarca());
            stmt.setString(2, z.getModelo());
            stmt.setFloat(3, z.getTalla());
            stmt.setDouble(4, z.getPrecio());
            stmt.setInt(5, z.getStock());
            stmt.setInt(6, z.getIdZapatilla());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Elimina una zapatilla según su ID
    @Override
    public boolean eliminarZapatilla(int id) {
        String sql = "DELETE FROM zapatillas WHERE id_zapatilla = ?";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
