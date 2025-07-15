
package org.kevinramirez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class LoginController implements Initializable {

   @FXML private Button btninicio, btnRegistro; 
   private Main principal;
   
   public void setPrincipal(Main principal) {
        this.principal = principal;
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
     public void ingresoA(ActionEvent evento){
         
        if (evento.getSource() == btninicio) {
            principal.mostrarInicio();
             
        } else if(evento.getSource() == btnRegistro){
            principal.mostrarRegistro();
        }
        
    }
}
