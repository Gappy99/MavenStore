
package org.kevinramirez.model;

/**
 *
 * @author edy14
 */
public class Cliente {
    
    private int idCliente;
    private String nombreCliente, apellidoCliente, nitCliente, correoCliente, contrasenaCliente, telefonoCliente; 

    public Cliente(int idCliente, String nombreCliente, String apellidoCliente, String nitCliente, String correoCliente, String contrasenaCliente, String telefonoCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.nitCliente = nitCliente;
        this.correoCliente = correoCliente;
        this.contrasenaCliente = contrasenaCliente;
        this.telefonoCliente = telefonoCliente;
    }

    public Cliente(int idCliente, String nombreCliente, String apellidoCliente, String nitCliente, String correoCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.nitCliente = nitCliente;
        this.correoCliente = correoCliente;
    }

    
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getContrasenaCliente() {
        return contrasenaCliente;
    }

    public void setContrasenaCliente(String contrasenaCliente) {
        this.contrasenaCliente = contrasenaCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }    
    
}
