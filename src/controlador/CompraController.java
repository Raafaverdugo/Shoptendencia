package controlador;

import modelo.Compra;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraController implements ICompraController {

    @Override
    public boolean registrarCompra(int idUsuario, int idZapatilla, Date fechaCompra, int cantidad, double total) {
        String sql = "INSERT INTO compras (id_usuario, id_zapatilla, fecha_compra, cantidad, total) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idZapatilla);
            stmt.setDate(3, new java.sql.Date(fechaCompra.getTime()));
            stmt.setInt(4, cantidad);
            stmt.setDouble(5, total);

            int filas = stmt.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<Compra> obtenerComprasPorCliente(int idCliente) {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT c.id_compra, c.fecha_compra, c.cantidad, c.total, z.modelo " +
                "FROM compras c " +
                "JOIN zapatillas z ON c.id_zapatilla = z.id_zapatilla " +
                "WHERE c.id_usuario = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Compra compra = new Compra();
                compra.setFechaCompra(rs.getDate("fecha_compra"));
                compra.setCantidad(rs.getInt("cantidad"));
                compra.setTotal(rs.getDouble("total"));
                compra.setModeloZapatilla(rs.getString("modelo")); // nuevo campo

                compras.add(compra);
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
