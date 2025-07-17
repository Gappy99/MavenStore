
package org.kevinramirez.model;

/**
 *
 * @author edy14
 */
public class Bebida {
    private int idBebida;
    private String tipoBebida, nombreBebida;
    private double precioBebida;

    public Bebida(int idBebida, String nombreBebida, String tipoBebida, double precioBebida) {
        this.idBebida = idBebida;
        this.nombreBebida = nombreBebida;
        this.tipoBebida = tipoBebida;
        this.precioBebida = precioBebida;
    }

    public int getIdBebida() {
        return idBebida;
    }

    public String getNombreBebida() {
        return nombreBebida;
    }
    
    public String getTipoBebida() {
        return tipoBebida;
    }

    public double getPrecioBebida() {
        return precioBebida;
    }

    public void setIdBebida(int idBebida) {
        this.idBebida = idBebida;
    }

    public void setTipoBebida(String tipoBebida) {
        this.tipoBebida = tipoBebida;
    }

    public void setNombreBebida(String nombreBebida) {
        this.nombreBebida = nombreBebida;
    }

    public void setPrecioBebida(double precioBebida) {
        this.precioBebida = precioBebida;
    }

    @Override
    public String toString() {
        return nombreBebida + " - Q" + precioBebida;
    }
}
