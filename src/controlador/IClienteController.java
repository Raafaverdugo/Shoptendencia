package controlador;

import modelo.Cliente;
import java.util.List;

public interface IClienteController {
    boolean registrarCliente(Cliente cliente);
    boolean actualizarCliente(Cliente cliente);
    boolean eliminarCliente(int idCliente);
    Cliente obtenerClientePorId(int idCliente);
    List<Cliente> obtenerTodosLosClientes();
}
