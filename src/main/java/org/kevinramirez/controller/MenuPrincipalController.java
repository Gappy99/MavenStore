
package org.kevinramirez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kevinramirez.database.Conexion;
import org.kevinramirez.model.Bebida;
import org.kevinramirez.model.Comida;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class MenuPrincipalController implements Initializable {

    @FXML private ListView<Comida> listComidas;
    @FXML private ListView<Bebida> listBebidas;
    @FXML private ListView<String> listCarrito;

    @FXML private Label lblTipo;
    @FXML private Label lblPrecio;
    @FXML private Label lblTotal;

    private ObservableList<Comida> comidas;
    private ObservableList<Bebida> bebidas;
    private ObservableList<String> carrito;

    private double total = 0.0;
    
    private Main principal;
    
     public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override public void initialize(URL url, ResourceBundle rb) {
        comidas = FXCollections.observableArrayList();
        bebidas = FXCollections.observableArrayList();
        carrito = FXCollections.observableArrayList();

        listComidas.setItems(comidas);
        listBebidas.setItems(bebidas);
        listCarrito.setItems(carrito);

        cargarComidas();
        cargarBebidas();

        listComidas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lblTipo.setText("Tipo: " + newVal.getTipoComida());
                lblPrecio.setText(String.format("Precio: $%.2f", newVal.getPrecioComida()));
                listBebidas.getSelectionModel().clearSelection();
            }
        });

        listBebidas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lblTipo.setText("Tipo: " + newVal.getTipoBebida());
                lblPrecio.setText(String.format("Precio: $%.2f", newVal.getPrecioBebida()));
                listComidas.getSelectionModel().clearSelection();
            }
        });
    }

    private void cargarComidas() {
        try {
            Connection con = Conexion.getInstancia().getConexion();
            CallableStatement stmt = con.prepareCall("{call sp_listarComidas()}");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Comida c = new Comida(
                    rs.getInt("ID"),
                    rs.getString("COMIDA"),
                    rs.getString("TIPO"),
                    rs.getDouble("PRECIO")
                );
                comidas.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarBebidas() {
        try {
            Connection con = Conexion.getInstancia().getConexion();
            CallableStatement stmt = con.prepareCall("{call sp_listarBebidas()}");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bebida b = new Bebida(
                    rs.getInt("ID"),
                    rs.getString("BEBIDA"),
                    rs.getString("TIPO"),
                    rs.getDouble("PRECIO")
                );
                bebidas.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void agregarAlCarrito() {
        Comida comidaSeleccionada = listComidas.getSelectionModel().getSelectedItem();
        Bebida bebidaSeleccionada = listBebidas.getSelectionModel().getSelectedItem();

        if (comidaSeleccionada != null) {
            carrito.add("Comida: " + comidaSeleccionada.getNombreComida() + " - $" + comidaSeleccionada.getPrecioComida());
            total += comidaSeleccionada.getPrecioComida();
            listComidas.getSelectionModel().clearSelection();
            lblTipo.setText("Tipo:");
            lblPrecio.setText("Precio:");
        } else if (bebidaSeleccionada != null) {
            carrito.add("Bebida: " + bebidaSeleccionada.getNombreBebida() + " - $" + bebidaSeleccionada.getPrecioBebida());
            total += bebidaSeleccionada.getPrecioBebida();
            listBebidas.getSelectionModel().clearSelection();
            lblTipo.setText("Tipo:");
            lblPrecio.setText("Precio:");
        }

        lblTotal.setText(String.format("$%.2f", total));
    }

    @FXML
    private void eliminarDelCarrito() {
        int index = listCarrito.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            String item = carrito.remove(index);
            // Aquí podrías parsear el precio para restarlo del total
            // Ejemplo básico:
            String precioStr = item.substring(item.lastIndexOf("$") + 1);
            try {
                double precio = Double.parseDouble(precioStr);
                total -= precio;
                lblTotal.setText(String.format("$%.2f", total));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void realizarPedido() {
        // Aquí la lógica para insertar el pedido en la base de datos,
                
        // incluyendo el detalle de cada ítem en el carrito (comida y bebida).
        // Este paso lo podemos hacer juntos cuando quieras.

        System.out.println("Pedido realizado por un total de: " + lblTotal.getText());
        carrito.clear();
        total = 0.0;
        lblTotal.setText("$0.00");
    }
    
    /*
    @FXML private void pedidos(){
        int idCliente = obtenerIdClienteSeleccionado();
        int idEmpleado = obtenerIdEmpleado();
        
        if (idCliente > 0 && idEmpleado > 0) {
            realizarPedido(idCliente, idEmpleado); 
        } else {
            System.out.println("Cliente o empleado no valido");
        }
    }*/
}
    