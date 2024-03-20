package es.studium.Gastos;

import java.math.BigDecimal;
import java.util.Date;

public class Compra {
    private int id;
    private Date fecha;
    private BigDecimal importe;
    private String nombreTienda;

    public Compra() {
        // Constructor por defecto
    }

    public Compra(int id, Date fecha, BigDecimal importe, String nombreTienda) {
        this.id = id;
        this.fecha = fecha;
        this.importe = importe;
        this.nombreTienda = nombreTienda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }
}
