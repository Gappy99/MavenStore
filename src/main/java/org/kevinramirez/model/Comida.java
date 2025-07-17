
package org.kevinramirez.model;

/**
 *
 * @author edy14
 */
public class Comida {
    
    private int idComida;
    private String tipoComida, nombreComida;
    private double precioComida;

    public Comida(int idComida, String nombreComida, String tipoComida, double precioComida) {
        this.idComida = idComida;
        this.nombreComida = nombreComida;
        this.tipoComida = tipoComida;
        this.precioComida = precioComida;
    }

    public int getIdComida() {
        return idComida;
    }

    public String getNombreComida() {
        return nombreComida;
    }

    public void setNombreComida(String nombreComida) {
        this.nombreComida = nombreComida;
    }

    
    public String getTipoComida() {
        return tipoComida;
    }

    public double getPrecioComida() {
        return precioComida;
    }

    public void setIdComida(int idComida) {
        this.idComida = idComida;
    }

    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }

    public void setPrecioComida(double precioComida) {
        this.precioComida = precioComida;
    }

    
    @Override
    public String toString() {
        return nombreComida + " - Q" + precioComida;
    }
}