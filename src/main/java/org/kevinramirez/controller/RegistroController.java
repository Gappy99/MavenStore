
package org.kevinramirez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.kevinramirez.database.Conexion;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class RegistroController implements Initializable {
    
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtNit;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtPassword;
    @FXML private TextField txtTelefono;
     @FXML private Button btnRegistrarme;
    
   private Main principal;
    
     public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    

    @FXML
    public void registrarCliente() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String nit = txtNit.getText();
        String correo = txtCorreo.getText();
        String password = txtPassword.getText();
        String telefono = txtTelefono.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || nit.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Debes completar todos los campos.");
            return;
        }
        
        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement cs = conn.prepareCall("{call sp_agregarCliente(?, ?, ?, ?, ?)}");
            cs.setString(1, nombre);
            cs.setString(2, apellido);
            cs.setString(3, nit);
            cs.setString(4, correo);
            cs.setString(5, password);
            cs.setString(6, telefono);
            cs.execute();

            mostrarAlerta("Registro exitoso", "Cliente registrado correctamente.");
            limpiarCampos();
            
           principal.mostrarInicio();
           
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo registrar el cliente: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtNit.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtPassword.clear();
    }
    
}
