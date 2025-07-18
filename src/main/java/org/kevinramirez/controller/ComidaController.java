
package org.kevinramirez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kevinramirez.database.Conexion;
import org.kevinramirez.model.Comida;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class ComidaController implements Initializable {

    @FXML private Button btnRegresar, btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnBuscar;
    @FXML private TableView<Comida> tablaComida;
    @FXML private TableColumn colId, colNombre, colTipo, colPrecio;
    @FXML private TextField txtId, txtNombre, txtTipo, txtPrecio, txtBuscar;
     
    private ObservableList<Comida> listarComida;
    private Main principal; 
    private Comida modeloComida;
    private enum Acciones {AGREGAR, EDITAR, ELIMINAR, NINGUNA}
    private Acciones accion = Acciones.NINGUNA;
     
    public void setPrincipal(Main principal){
        this.principal = principal;
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFormatoCeldasModelo(); 
        cargarDatos();
        tablaComida.setOnMouseClicked(eventHandler -> getClienteTextField());
        
        txtId.setDisable(true);
        txtNombre.setDisable(true);
        txtTipo.setDisable(true);
        txtPrecio.setDisable(true);
    }

   
    private void setFormatoCeldasModelo() {
        
        colId.setCellValueFactory(new PropertyValueFactory<Comida, Integer>("idComida"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Comida, String>("nombreComida"));
        colTipo.setCellValueFactory(new PropertyValueFactory<Comida, String>("tipoComida"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Comida, String>("precioComida"));
    }

    private void cargarDatos() {
      ArrayList<Comida> clientes = ListarComidas();
        listarComida = FXCollections.observableArrayList(clientes);
        tablaComida.setItems(listarComida);
        tablaComida.getSelectionModel().selectFirst();
        getClienteTextField();
    }
    
    public void getClienteTextField(){
        Comida comidaSeleccionada = tablaComida.getSelectionModel().getSelectedItem();
        if (comidaSeleccionada != null) {
        
        txtId.setText(String.valueOf(comidaSeleccionada.getIdComida()));
        txtNombre.setText(comidaSeleccionada.getNombreComida());
        txtTipo.setText(comidaSeleccionada.getTipoComida());
        txtPrecio.setText(String.valueOf(comidaSeleccionada.getPrecioComida()));
        }
    }
    
    private ArrayList<Comida> ListarComidas () {
        ArrayList<Comida> comida = new ArrayList<>();
        try {
            Connection conexionv = Conexion.getInstancia().getConexion();
            String sql = "{call sp_listarComidas()}";
            CallableStatement enunciado = conexionv.prepareCall(sql);
            ResultSet resultado = enunciado.executeQuery();
            while (resultado.next()) {                
                comida.add(new Comida(
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getInt(4)));
            }
            
        } catch (SQLException e) {
            System.out.println("error al cargar: " + e.getMessage());
        }
        return comida;
    }
    
    private Comida getModeloComida(){
        int id;
        if (txtId.getText().isEmpty()) {
            id =1;
        } else {
            id = Integer.parseInt(txtId.getText());
        }
        
        double precio; 
        if (txtPrecio.getText().isEmpty()) {
            precio = 10.2; 
        } else {
            precio = Double.parseDouble(txtId.getText()); 
        }
        
        String nombre = txtNombre.getText();
        String apellido = txtTipo.getText();
        Comida comida = new Comida(id, nombre, apellido, precio);
        
        return comida;
    }
    
    
    public void agregarComida(){
        if (txtNombre.getText().isEmpty() || txtTipo.getText().isEmpty() 
                || txtPrecio.getText().isEmpty()){
           // Crear una alerta de advertencia si falta algún campo
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Por favor, llena todos los campos");
            alert.setContentText("Debe completar todos los campos antes de agregar al Cliente");
            alert.showAndWait();
        } else {
            modeloComida = getModeloComida();

            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarComida(?,?,?)}");
                enunciado.setString(1, modeloComida.getNombreComida());
                enunciado.setString(2, modeloComida.getTipoComida());
                enunciado.setDouble(3, modeloComida.getPrecioComida());
                enunciado.execute();
                cargarDatos();
                cambiarOriginal();
                habilitarDeshabilitarNodo();
                System.out.println(" Cliente agregado correctamente");
            } catch (SQLException ex) {
                System.out.println(" error al agregar Cliente: " + ex.getSQLState());
                ex.printStackTrace();
            }
        }
    }
    
    
    public void editarCliente(){
        if (txtNombre.getText().isEmpty() || txtTipo.getText().isEmpty() 
                || txtPrecio.getText().isEmpty()) {
           
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Por favor, llena todos los campos");
            alert.setContentText("Debe completar todos los campos para terminar de editar");
            alert.showAndWait();
        } else{
                modeloComida = getModeloComida();
            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarComida(?,?,?,?)}");
                enunciado.setInt(1, modeloComida.getIdComida());
                enunciado.setString(2, modeloComida.getNombreComida());
                enunciado.setString(3, modeloComida.getTipoComida());
                enunciado.setDouble(4, modeloComida.getPrecioComida());
                enunciado.execute();
                cargarDatos();
                cambiarOriginal();
                habilitarDeshabilitarNodo();
                System.out.println(" Cliente editado correctamente");
            } catch (SQLException ex) {
                System.out.println(" Error al editar Cliente: " + ex.getSQLState());
                ex.printStackTrace();
            }
        }
    }
    
    public void eliminarCliente(){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion de cliente");
            alert.setHeaderText("¿Deceas eliminar este Cliente?");
            Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK){
            modeloComida = getModeloComida();
            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarCliente(?)}");
                enunciado.setInt(1, modeloComida.getIdComida());
                enunciado.execute();
                cargarDatos();
            } catch (SQLException e) {
                System.out.println("Error al eliminar " + e.getMessage());
                e.printStackTrace();
            }
        }
    }    
    
    public void limpiarTexto(){
        txtId.clear();
        txtNombre.clear();
        txtTipo.clear();
        txtPrecio.clear();
    }
    
    public void btnRegresarActionEvent(ActionEvent evento){
        System.out.println("regreso a Administracion");
       
    }
    @FXML
    private void cambiarNuevoCliente(){
        if (null != accion) switch (accion) {
            case NINGUNA:
                cambiarGuardarEditar();
                accion = Acciones.AGREGAR;
                limpiarTexto();
                habilitarDeshabilitarNodo();
                break;
            case AGREGAR:
                System.out.println(" accion de metodo agregar ");
                agregarComida();
                break;
            case EDITAR:
                System.out.println(" Accion del metodo editar");
                editarCliente();
                break;
            default:
                break;
        }
    }
    
    @FXML
    public void cambiarEdicionCliente(){
        cambiarGuardarEditar();
        accion = Acciones.EDITAR;
        habilitarDeshabilitarNodo();
    }
    
    @FXML
    public void cambiarCancelarEliminar(){
        if (accion == Acciones.NINGUNA) {
            eliminarCliente();
        } else if (accion == Acciones.AGREGAR || accion == Acciones.EDITAR) {
            cambiarOriginal();
            habilitarDeshabilitarNodo();
        }
    }
    
    @FXML
    private void btnSiguienteAccion(){
        int indice = tablaComida.getSelectionModel().getSelectedIndex();
        if (indice < listarComida.size()-1) {
            tablaComida.getSelectionModel().select(indice+1);
            getClienteTextField();
        }
    }
    
    @FXML
    private void btnAnteriorAccion(){
        int indice = tablaComida.getSelectionModel().getSelectedIndex();
        if (indice > 0) {
            tablaComida.getSelectionModel().select(indice-1);
            getClienteTextField();
        }
    }
    
    public void cambiarGuardarEditar(){
        btnNuevo.setText("Guardar");
        btnEliminar.setText("Cancelar");
        btnEditar.setDisable(true);
    }
    
    public void cambiarOriginal(){
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setDisable(false);
        accion = Acciones.NINGUNA;
    }
    
    private void cambiarEstado(boolean estado){
        
        txtNombre.setDisable(estado);
        txtTipo.setDisable(estado);
        txtPrecio.setDisable(estado);
    }
    
    private void habilitarDeshabilitarNodo(){
        boolean deshabilitado = txtNombre.isDisable();
        cambiarEstado(!deshabilitado);
        btnSiguiente.setDisable(deshabilitado);
        btnAnterior.setDisable(deshabilitado);
        tablaComida.setDisable(deshabilitado);
    }
    
    @FXML
    private void btnBuscarPorNombre(){
        ArrayList<Comida> resultadoBusqueda = new ArrayList<>();
        String nombreBuscado = txtBuscar.getText();
        for(Comida cliente:listarComida){
            if (cliente.getNombreComida().toLowerCase().contains(nombreBuscado.toLowerCase())) {
                resultadoBusqueda.add(cliente);
            }
        }
        tablaComida.setItems(FXCollections.observableArrayList(resultadoBusqueda));
        if (resultadoBusqueda.isEmpty()) {
            tablaComida.getSelectionModel().selectFirst();
        }
    }
    
    @FXML private void refrescarLIsta(ActionEvent evento) {
        txtBuscar.clear();
        tablaComida.setItems(listarComida);

   }
}
