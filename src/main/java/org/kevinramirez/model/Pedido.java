
package org.kevinramirez.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author edy14
 */
public class Pedido {
    
    private int idPedido;
    private String nombreCliente;
    private String nombreEmpleado;
    private LocalDate fechaPedido;
    private String metodoPago;
    private String estadoFactura;

    public Pedido() {}

    public Pedido(int idPedido, String nombreCliente, String nombreEmpleado, LocalDate fechaPedido, String metodoPago, String estadoFactura) {
        this.idPedido = idPedido;
        this.nombreCliente = nombreCliente;
        this.nombreEmpleado = nombreEmpleado;
        this.fechaPedido = fechaPedido;
        this.metodoPago = metodoPago;
        this.estadoFactura = estadoFactura;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    
}
