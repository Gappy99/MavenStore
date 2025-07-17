
package org.kevinramirez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.kevinramirez.model.Pedido;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class PedidoController implements Initializable {
 @FXML private TableView<Pedido> tablaPedidos;
 
   @FXML private TableColumn colIdPedido, colCliente, colEmpleado, colFecha, colMetodoPago, colEstadoFactura ; 

    private ObservableList<Pedido> listaPedidos;

    private Main principal;
    
     public void setPrincipal(Main principal) {
        this.principal = principal;
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        colIdPedido.setCellValueFactory(new PropertyValueFactory<Pedido, Integer>("idPedido"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Pedido, String>("nombreCliente"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Pedido, String>("nombreEmpleado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Pedido, LocalDate>("fechaPedido"));
        colMetodoPago.setCellValueFactory(new PropertyValueFactory<Pedido, String>("metodoPago"));
        colEstadoFactura.setCellValueFactory(new PropertyValueFactory<Pedido, String>("estadoFactura"));
        listaPedidos = FXCollections.observableArrayList(listarPedidos());

        tablaPedidos.setItems(listaPedidos);
    }

   private ArrayList<Pedido> listarPedidos () {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        try {
            Connection conexionv = Conexion.getInstancia().getConexion();
            String sql = "{call sp_listarPedidos()}";
            CallableStatement enunciado = conexionv.prepareCall(sql);
            ResultSet resultado = enunciado.executeQuery();
            while (resultado.next()) {                
                pedidos.add(new Pedido(
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getDate(4).toLocalDate(),
                        resultado.getString(5),
                        resultado.getString(6)));
            }
            
        } catch (SQLException e) {
            System.out.println("error al cargar: " + e.getMessage());
        }
        return pedidos;
    }
}
