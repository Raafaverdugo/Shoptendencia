package controlador;

import modelo.Zapatilla;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZapatillaController implements IZapatillaService {

    @Override
    public List<Zapatilla> obtenerTodas() {
        List<Zapatilla> lista = new ArrayList<>();
        String sql = "SELECT * FROM zapatillas";

        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Zapatilla z = new Zapatilla(
                        rs.getInt("id_zapatilla"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getFloat("precio"),
                        rs.getInt("stock"),
                        rs.getInt("talla")
                );
                lista.add(z);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Zapatilla buscarPorId(int id) {
        Zapatilla z = null;
        String sql = "SELECT * FROM zapatillas WHERE id_zapatilla = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                z = new Zapatilla(
                        rs.getInt("id_zapatilla"),
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getFloat("precio"),
                        rs.getInt("stock"),
                        rs.getInt("talla")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return z;
    }

    @Override
    public boolean agregarZapatilla(Zapatilla z) {
        String sql = "INSERT INTO zapatillas (modelo, marca, precio, stock, talla) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, z.getModelo());
            stmt.setString(2, z.getMarca());
            stmt.setDouble(3, z.getPrecio());
            stmt.setInt(4, z.getStock());
            stmt.setFloat(5, z.getTalla());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean actualizarZapatilla(Zapatilla z) {
        String sql = "UPDATE zapatillas SET modelo = ?, marca = ?, precio = ?, stock = ?, talla = ? WHERE id_zapatilla = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, z.getModelo());
            stmt.setString(2, z.getMarca());
            stmt.setDouble(3, z.getPrecio());
            stmt.setInt(4, z.getStock());
            stmt.setFloat(5, z.getTalla());
            stmt.setInt(6, z.getIdZapatilla());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean eliminarZapatilla(int id) {
        String sql = "DELETE FROM zapatillas WHERE id_zapatilla = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
