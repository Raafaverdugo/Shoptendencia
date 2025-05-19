package controlador;

import modelo.Compra;
import java.util.List;

public interface ICompraController {
    boolean registrarCompra(Compra compra);
    List<Compra> obtenerComprasPorCliente(int idCliente);
    List<Compra> obtenerTodasLasCompras(); // Solo para admin
    double calcularTotalGastadoPorCliente(int idCliente);
    double calcularIngresosTotales();
}
