package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/shoptendencia";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER); // Cargar el driver una vez al iniciar la clase
            System.out.println("✅ Driver MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ No se encontró el driver de MySQL.");
            e.printStackTrace();
        }
    }

    public static Connection obtenerConexion() throws SQLException {
        // Devuelve una nueva conexión cada vez que se llama
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }
}
