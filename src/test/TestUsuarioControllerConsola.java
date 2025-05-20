package test;

import controlador.UsuarioController;
import modelo.Usuario;

import java.util.Scanner;

public class TestUsuarioControllerConsola {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UsuarioController usuarioCtrl = new UsuarioController();

        Usuario usuarioLogueado = null;

        while (true) {
            System.out.println("\n--- MENÚ USUARIO ---");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar nuevo cliente");
            System.out.println("3. Comprobar si es admin (requiere login)");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre de usuario: ");
                    String nombreUsuario = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String contraseña = sc.nextLine();

                    usuarioLogueado = usuarioCtrl.login(nombreUsuario, contraseña);
                    break;

                case 2:
                    System.out.print("Elige nombre de usuario: ");
                    String nuevoUsuario = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String nuevaPass = sc.nextLine();

                    Usuario nuevo = new Usuario(nuevoUsuario, nuevaPass, "cliente");
                    boolean exito = usuarioCtrl.registrarCliente(nuevo);
                    if (exito) {
                        System.out.println("✅ Cliente registrado correctamente.");
                    } else {
                        System.out.println("❌ Error al registrar cliente.");
                    }
                    break;

                case 3:
                    if (usuarioLogueado == null) {
                        System.out.println("⚠️ Debes iniciar sesión primero.");
                    } else if (usuarioCtrl.esAdmin(usuarioLogueado)) {
                        System.out.println("✅ El usuario es ADMIN.");
                    } else {
                        System.out.println("👤 El usuario no es admin (cliente).");
                    }
                    break;

                case 4:
                    System.out.println("👋 Cerrando prueba de UsuarioController...");
                    sc.close();
                    return;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
