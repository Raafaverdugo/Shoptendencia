package controlador;

import modelo.Compra;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraController implements ICompraController {


     // Registra una nueva compra en la base de datos.

    @Override
    public boolean registrarCompra(int idUsuario, int idZapatilla, Date fechaCompra, int cantidad, double total) {
        String sql = "INSERT INTO compras (id_usuario, id_zapatilla, fecha_compra, cantidad, total) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = ConexionBD.obtenerConexion(); // Se obtiene conexión a la base de datos
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Se asignan los valores a los parámetros del SQL
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idZapatilla);
            stmt.setDate(3, new java.sql.Date(fechaCompra.getTime()));
            stmt.setInt(4, cantidad);
            stmt.setDouble(5, total);

            int filas = stmt.executeUpdate(); // Ejecuta la inserción

            return filas > 0; // Retorna true si se insertó al menos una fila

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime error si falla
        }

        return false;
    }

     // Obtiene la lista de compras realizadas por un cliente específico.

    @Override
    public List<Compra> obtenerComprasPorCliente(int idCliente) {
        List<Compra> compras = new ArrayList<>();
        // Consulta SQL con JOIN para obtener modelo de la zapatilla asociada
        String sql = "SELECT c.id_compra, c.fecha_compra, c.cantidad, c.total, z.modelo " +
                "FROM compras c " +
                "JOIN zapatillas z ON c.id_zapatilla = z.id_zapatilla " +
                "WHERE c.id_usuario = ?";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, idCliente); // Se asigna el ID del cliente
            ResultSet rs = stmt.executeQuery(); // Se ejecuta la consulta

            // Recorre los resultados y crea objetos Compra
            while (rs.next()) {
                Compra compra = new Compra();
                compra.setFechaCompra(rs.getDate("fecha_compra"));
                compra.setCantidad(rs.getInt("cantidad"));
                compra.setTotal(rs.getDouble("total"));
                compra.setModeloZapatilla(rs.getString("modelo"));

                compras.add(compra); // Se agrega la compra a la lista
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compras;
    }

     //Calcula la suma total de ingresos generados por todas las compras.

    @Override
    public double calcularIngresosTotales() {
        double total = 0.0;
        String sql = "SELECT SUM(total) AS total FROM compras";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.next()) {
                total = rs.getDouble("total"); // Extrae el total acumulado
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
}
