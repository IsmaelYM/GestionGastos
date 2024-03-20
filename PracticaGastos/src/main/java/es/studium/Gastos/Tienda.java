package es.studium.Gastos;

public class Tienda {
    private int id;
    private String nombreTienda;

    // Constructor, getters y setters

    public Tienda() {
    }

    public Tienda(int id, String nombreTienda) {
        this.id = id;
        this.nombreTienda = nombreTienda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }
}