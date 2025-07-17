
package org.kevinramirez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kevinramirez.database.Conexion;
import org.kevinramirez.model.DetallePedido;
import org.kevinramirez.model.Pedido;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class DetallePedidoController implements Initializable {

   @FXML private TableView<DetallePedido> tablaDetallePedido;

    @FXML private TableColumn colIdDetalle, colIdPedido, colIdComida, colIdBebida, colCantidad;

    private Main principal;
    
     public void setPrincipal(Main principal) {
        this.principal = principal;
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tablaDetallePedido.setItems(getDetallePedidos());

        colIdDetalle.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("idDetalle"));
        colIdPedido.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("idPedido"));
        colIdComida.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("idComida"));
        colIdBebida.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("idBebida"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("cantidad"));
    }

    public ObservableList<DetallePedido> getDetallePedidos() {
        ArrayList<DetallePedido> lista = new ArrayList<>();

        try {
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().
                    prepareCall("{call sp_listarDetallePedidos()}");
            ResultSet resultado = procedimiento.executeQuery();

            while (resultado.next()) {
                lista.add(new DetallePedido(
                        resultado.getInt(1),
                        resultado.getInt(2),
                        resultado.getInt(3),
                        resultado.getInt(4),
                        resultado.getInt(5)
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return FXCollections.observableList(lista);
    }
}

