
package org.kevinramirez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.kevinramirez.model.Comida;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class MenuPrincipalController implements Initializable {

    @FXML private ListView<Comida> listViewComidas;

    @FXML private Label labelDetalles;

    private ObservableList<Comida> listaComidas;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Cargar lista de comidas, aquí con datos simulados
        listaComidas = FXCollections.observableArrayList(
            new Comida(1, "Hamburguesa Clásica", "Comida", "Coca-Cola", 45.00),
            new Comida(2, "Pizza Personal", "Comida", "Pepsi", 50.00),
            new Comida(3, "Ensalada César", "Entrada", "", 30.00)
        );

        // Vincular lista al ListView
        listViewComidas.setItems(listaComidas);

        // Selección: cuando el usuario selecciona un ítem, mostramos detalles
        listViewComidas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mostrarDetalles(newVal);
            }
        });
    }

    private void mostrarDetalles(Comida comida) {
        String detalles = "Nombre: " + comida.getNombreComida() + "\n"
                        + "Tipo: " + comida.getTipo() + "\n"
                        + "Bebida: " + (comida.getNombreBebida().isEmpty() ? "Ninguna" : comida.getNombreBebida()) + "\n"
                        + "Precio: Q" + comida.getPrecio();
        labelDetalles.setText(detalles);
    }
}
