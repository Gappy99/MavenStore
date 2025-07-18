
package org.kevinramirez.model;

/**
 *
 * @author edy14
 */
public class Empleado {
    private int idEmpleado; 
    private String nombreEmpleado, apellidoEmpleado, puestoEmpleado, telefonoEmpleado, correoEmpleado; 
    private double sueldoEmpleado; 

    public Empleado(int idEmpleado, String nombreEmpleado, String apellidoEmpleado, String puestoEmpleado, String telefonoEmpleado, String correoEmpleado, double sueldoEmpleado) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.puestoEmpleado = puestoEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
        this.correoEmpleado = correoEmpleado;
        this.sueldoEmpleado = sueldoEmpleado;
    }

    public Empleado(int idEmpleado, String nombreEmpleado, String apellidoEmpleado) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
    }
    
    
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getPuestoEmpleado() {
        return puestoEmpleado;
    }

    public void setPuestoEmpleado(String puestoEmpleado) {
        this.puestoEmpleado = puestoEmpleado;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public String getCorreoEmpleado() {
        return correoEmpleado;
    }

    public void setCorreoEmpleado(String correoEmpleado) {
        this.correoEmpleado = correoEmpleado;
    }

    public double getSueldoEmpleado() {
        return sueldoEmpleado;
    }

    public void setSueldoEmpleado(double sueldoEmpleado) {
        this.sueldoEmpleado = sueldoEmpleado;
    }
    
    
}
