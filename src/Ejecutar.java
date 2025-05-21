import vista.PantallaInicio;

import javax.swing.*;

public class Ejecutar {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaInicio().setVisible(true));
    }
}
