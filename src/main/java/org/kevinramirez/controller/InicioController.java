
package org.kevinramirez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kevinramirez.database.Conexion;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class InicioController implements Initializable {

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasena;
    
    private Main principal;
    
     public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    

    @FXML
    void clicIngresar(ActionEvent event) {
        String correo = txtCorreo.getText();
        String contrasena = txtContrasena.getText();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error", "Debes completar todos los campos.");
            return;
        }

        try {
            Connection cn = Conexion.getInstancia().getConexion();
            CallableStatement procedimiento = cn.prepareCall("{CALL sp_verificarLogin(?,?)}");
            procedimiento.setString(1, correo);
            procedimiento.setString(2, contrasena);

            ResultSet resultado = procedimiento.executeQuery();

            if (resultado.next()) {
                // Acceso exitoso
               principal.mostrarMenuPrincipal();
            } else {
                mostrarAlerta("Acceso Denegado", "Correo o contraseña incorrectos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo verificar el usuario.");
        }
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
}
