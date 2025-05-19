package controlador;

import modelo.Usuario;

public interface IUsuarioController {
    Usuario login(String nombreUsuario, String contraseña);
    boolean esAdmin(Usuario usuario);
    boolean registrarCliente(Usuario usuario);
}
