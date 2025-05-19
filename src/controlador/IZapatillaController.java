package controlador;

import modelo.Zapatilla;
import java.util.List;

public interface IZapatillaController {
    List<Zapatilla> obtenerTodas();
    Zapatilla buscarPorId(int id);
    boolean agregarZapatilla(Zapatilla z);
    boolean actualizarZapatilla(Zapatilla z);
    boolean eliminarZapatilla(int id);
}
