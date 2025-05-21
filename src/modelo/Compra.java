package modelo;

import java.sql.Date;

public class Compra {
    private int idCompra;
    private int idCliente;
    private int idZapatilla;
    private Date fechaCompra;
    private int cantidad;
    private double total;

    public Compra(int idCompra, int idCliente, int idZapatilla, Date fechaCompra, int cantidad, double total) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.idZapatilla = idZapatilla;
        this.fechaCompra = fechaCompra;
        this.cantidad = cantidad;
        this.total = total;
    }

    public Compra(int idCliente, int idZapatilla, Date fechaCompra, int cantidad, double total) {
        this(0, idCliente, idZapatilla, fechaCompra, cantidad, total);
    }

    public Compra() {

    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdZapatilla() {
        return idZapatilla;
    }

    public void setIdZapatilla(int idZapatilla) {
        this.idZapatilla = idZapatilla;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Compra #" + idCompra + " - Cliente " + idCliente + " - Zapatilla " + idZapatilla;
    }
}
