
package org.kevinramirez.model;

/**
 *
 * @author edy14
 */
public class DetallePedido {
     private int idDetalle;
    private int idPedido;
    private int idComida;
    private int idBebida;
    private int cantidad;

    public DetallePedido() {
    }

    public DetallePedido(int idDetalle, int idPedido, int idComida, int idBebida, int cantidad) {
        this.idDetalle = idDetalle;
        this.idPedido = idPedido;
        this.idComida = idComida;
        this.idBebida = idBebida;
        this.cantidad = cantidad;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdComida() {
        return idComida;
    }

    public void setIdComida(int idComida) {
        this.idComida = idComida;
    }

    public int getIdBebida() {
        return idBebida;
    }

    public void setIdBebida(int idBebida) {
        this.idBebida = idBebida;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
