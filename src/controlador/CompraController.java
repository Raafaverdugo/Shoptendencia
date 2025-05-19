package controlador;

import modelo.Compra;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraController implements ICompraController {

    @Override
    public boolean registrarCompra(Compra compra) {
        String sql = "INSERT INTO compras (id_cliente, id_zapatilla, fecha_compra, cantidad, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, compra.getIdCliente());
            stmt.setInt(2, compra.getIdZapatilla());
            stmt.setDate(3, compra.getFechaCompra());
            stmt.setInt(4, compra.getCantidad());
            stmt.setDouble(5, compra.getTotal());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Compra> obtenerComprasPorCliente(int idCliente) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras WHERE id_cliente = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Compra c = new Compra(
                        rs.getInt("id_compra"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_zapatilla"),
                        rs.getDate("fecha_compra"),
                        rs.getInt("cantidad"),
                        rs.getDouble("total")
                );
                compras.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compras;
    }

    @Override
    public List<Compra> obtenerTodasLasCompras() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras";

        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Compra c = new Compra(
                        rs.getInt("id_compra"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_zapatilla"),
                        rs.getDate("fecha_compra"),
                        rs.getInt("cantidad"),
                        rs.getDouble("total")
                );
                compras.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compras;
    }

    @Override
    public double calcularTotalGastadoPorCliente(int idCliente) {
        double total = 0.0;
        String sql = "SELECT SUM(total) AS total FROM compras WHERE id_cliente = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    @Override
    public double calcularIngresosTotales() {
        double total = 0.0;
        String sql = "SELECT SUM(total) AS total FROM compras";

        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
}
