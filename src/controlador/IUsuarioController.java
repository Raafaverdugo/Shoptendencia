package controlador;

import modelo.Usuario;

public interface IUsuarioController {
    // Métodos
    Usuario login(String nombreUsuario, String contraseña);
    boolean esAdmin(Usuario usuario);
    Usuario registrarUsuario(Usuario usuario);
}
