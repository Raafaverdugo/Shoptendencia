package controlador;

import modelo.Compra;

import java.sql.Date;
import java.util.List;

public interface ICompraController {
    boolean registrarCompra(int idUsuario, int idZapatilla, Date fechaCompra, int cantidad, double total);
    List<Compra> obtenerComprasPorCliente(int idCliente);
    List<Compra> obtenerTodasLasCompras();
    double calcularIngresosTotales();
}
