
package org.kevinramirez.model;

/**
 *
 * @author edy14
 */
public class Comida {
    
    private int idComida;
    private String nombreComida;  // nombre del platillo
    private String tipo;          // tipo de comida (por ejemplo: "principal", "entrada", "bebida")
    private String nombreBebida;  // nombre de bebida asociada o separada
    private double precio;

    public Comida(int idComida, String nombreComida, String tipo, String nombreBebida, double precio) {
        this.idComida = idComida;
        this.nombreComida = nombreComida;
        this.tipo = tipo;
        this.nombreBebida = nombreBebida;
        this.precio = precio;
    }

    public int getIdComida() {
        return idComida;
    }

    public String getNombreComida() {
        return nombreComida;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombreBebida() {
        return nombreBebida;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        // Aquí puedes decidir qué mostrar en el ListView; por ejemplo:
        String bebidaTexto = (nombreBebida == null || nombreBebida.isEmpty()) ? "" : " + Bebida: " + nombreBebida;
        return nombreComida + bebidaTexto + " - Q" + precio;
    }
}