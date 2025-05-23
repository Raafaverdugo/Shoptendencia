package modelo;

import java.sql.Date;

public class Compra {
    // Variables
    private int idCompra;
    private int idCliente;
    private int idZapatilla;
    private Date fechaCompra;
    private int cantidad;
    private double total;
    private String modeloZapatilla;

    // Constructores
    public Compra(int idCompra, int idCliente, int idZapatilla, Date fechaCompra, int cantidad, double total) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.idZapatilla = idZapatilla;
        this.fechaCompra = fechaCompra;
        this.cantidad = cantidad;
        this.total = total;
    }

    public Compra() {
    }

    // Getters y setters
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

    public String getModeloZapatilla() {
        return modeloZapatilla;
    }

    public void setModeloZapatilla(String modeloZapatilla) {
        this.modeloZapatilla = modeloZapatilla;
    }

    @Override
    public String toString() {
        return "Zapatilla: " + modeloZapatilla + ", Fecha: " + fechaCompra +
                ", Cantidad: " + cantidad + ", Total: $" + total;
    }
}
