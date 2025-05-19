package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/shoptendencia";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static Connection conexion;

    public static Connection obtenerConexion() {
        if (conexion == null) {
            try {
                Class.forName(DRIVER); // Cargar el driver de MySQL
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
                System.out.println("✅ Conexión establecida con la base de datos.");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ No se encontró el driver de MySQL.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("❌ Error al conectar con la base de datos.");
                e.printStackTrace();
            }
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("🔒 Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
}
