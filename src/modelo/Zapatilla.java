package modelo;

public class Zapatilla {
    private int idZapatilla;
    private String marca;
    private String modelo;
    private float talla;
    private double precio;
    private int stock;

    public Zapatilla(int idZapatilla, String marca, String modelo, float talla, double precio, int stock) {
        this.idZapatilla = idZapatilla;
        this.marca = marca;
        this.modelo = modelo;
        this.talla = talla;
        this.precio = precio;
        this.stock = stock;
    }

    public Zapatilla(String marca, String modelo, float talla, double precio, int stock) {
        this(0, marca, modelo, talla, precio, stock);
    }

    public int getIdZapatilla() {
        return idZapatilla;
    }

    public void setIdZapatilla(int idZapatilla) {
        this.idZapatilla = idZapatilla;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public float getTalla() {
        return talla;
    }

    public void setTalla(float talla) {
        this.talla = talla;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return marca + " " + modelo + " (Talla " + talla + ") - $" + precio;
    }
}
