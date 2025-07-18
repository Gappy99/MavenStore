
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
import org.kevinramirez.model.Bebida;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class BebidaController implements Initializable {

    @FXML private Button btnRegresar, btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnBuscar;
    @FXML private TableView<Bebida> tablaBebida;
    @FXML private TableColumn colId, colNombre, colTipo, colPrecio;
    @FXML private TextField txtId, txtNombre, txtTipo, txtPrecio, txtBuscar;
     
    private ObservableList<Bebida> listarBebida;
    private Main principal; 
    private Bebida modeloBebida;
    private enum Acciones {AGREGAR, EDITAR, ELIMINAR, NINGUNA}
    private Acciones accion = Acciones.NINGUNA;
     
    public void setPrincipal(Main principal){
        this.principal = principal;
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFormatoCeldasModelo(); 
        cargarDatos();
        tablaBebida.setOnMouseClicked(eventHandler -> getBebidaTextField());
        
        txtId.setDisable(true);
        txtNombre.setDisable(true);
        txtTipo.setDisable(true);
        txtPrecio.setDisable(true);
    }

   
    private void setFormatoCeldasModelo() {
        
        colId.setCellValueFactory(new PropertyValueFactory<Bebida, Integer>("idBebida"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Bebida, String>("nombreBebida"));
        colTipo.setCellValueFactory(new PropertyValueFactory<Bebida, String>("tipoBebida"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Bebida, String>("precioBebida"));
    }

    private void cargarDatos() {
      ArrayList<Bebida> clientes = ListarBebidas();
        listarBebida = FXCollections.observableArrayList(clientes);
        tablaBebida.setItems(listarBebida);
        tablaBebida.getSelectionModel().selectFirst();
        getBebidaTextField();
    }
    
    public void getBebidaTextField(){
        Bebida comidaSeleccionada = tablaBebida.getSelectionModel().getSelectedItem();
        if (comidaSeleccionada != null) {
        
        txtId.setText(String.valueOf(comidaSeleccionada.getIdBebida()));
        txtNombre.setText(comidaSeleccionada.getNombreBebida());
        txtTipo.setText(comidaSeleccionada.getTipoBebida());
        txtPrecio.setText(String.valueOf(comidaSeleccionada.getPrecioBebida()));
        }
    }
    
    private ArrayList<Bebida> ListarBebidas () {
        ArrayList<Bebida> bebida = new ArrayList<>();
        try {
            Connection conexionv = Conexion.getInstancia().getConexion();
            String sql = "{call sp_listarBebidas()}";
            CallableStatement enunciado = conexionv.prepareCall(sql);
            ResultSet resultado = enunciado.executeQuery();
            while (resultado.next()) {                
                bebida.add(new Bebida(
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getInt(4)));
            }
            
        } catch (SQLException e) {
            System.out.println("error al cargar: " + e.getMessage());
        }
        return bebida;
    }
    
    private Bebida getModeloBebida(){
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
        Bebida comida = new Bebida(id, nombre, apellido, precio);
        
        return comida;
    }
    
    
    public void agregarBebida(){
        if (txtNombre.getText().isEmpty() || txtTipo.getText().isEmpty() 
                || txtPrecio.getText().isEmpty()){
           // Crear una alerta de advertencia si falta algún campo
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Por favor, llena todos los campos");
            alert.setContentText("Debe completar todos los campos antes de agregar al Cliente");
            alert.showAndWait();
        } else {
            modeloBebida = getModeloBebida();

            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarBebida(?,?,?)}");
                enunciado.setString(1, modeloBebida.getNombreBebida());
                enunciado.setString(2, modeloBebida.getTipoBebida());
                enunciado.setDouble(3, modeloBebida.getPrecioBebida());
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
    
    
    public void editarBebida(){
        if (txtNombre.getText().isEmpty() || txtTipo.getText().isEmpty() 
                || txtPrecio.getText().isEmpty()) {
           
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Por favor, llena todos los campos");
            alert.setContentText("Debe completar todos los campos para terminar de editar");
            alert.showAndWait();
        } else{
                modeloBebida = getModeloBebida();
            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarBebida(?,?,?,?)}");
                enunciado.setInt(1, modeloBebida.getIdBebida());
                enunciado.setString(2, modeloBebida.getNombreBebida());
                enunciado.setString(3, modeloBebida.getTipoBebida());
                enunciado.setDouble(4, modeloBebida.getPrecioBebida());
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
    
    public void eliminarBebida(){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion de cliente");
            alert.setHeaderText("¿Deceas eliminar este Cliente?");
            Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK){
            modeloBebida = getModeloBebida();
            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarBebida(?)}");
                enunciado.setInt(1, modeloBebida.getIdBebida());
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
    private void cambiarNuevaBebida(){
        if (null != accion) switch (accion) {
            case NINGUNA:
                cambiarGuardarEditar();
                accion = Acciones.AGREGAR;
                limpiarTexto();
                habilitarDeshabilitarNodo();
                break;
            case AGREGAR:
                System.out.println(" accion de metodo agregar ");
                agregarBebida();
                break;
            case EDITAR:
                System.out.println(" Accion del metodo editar");
                editarBebida();
                break;
            default:
                break;
        }
    }
    
    @FXML
    public void cambiarEdicionBebida(){
        cambiarGuardarEditar();
        accion = Acciones.EDITAR;
        habilitarDeshabilitarNodo();
    }
    
    @FXML
    public void cambiarCancelarEliminar(){
        if (accion == Acciones.NINGUNA) {
            eliminarBebida();
        } else if (accion == Acciones.AGREGAR || accion == Acciones.EDITAR) {
            cambiarOriginal();
            habilitarDeshabilitarNodo();
        }
    }
    
    @FXML
    private void btnSiguienteAccion(){
        int indice = tablaBebida.getSelectionModel().getSelectedIndex();
        if (indice < listarBebida.size()-1) {
            tablaBebida.getSelectionModel().select(indice+1);
            getBebidaTextField();
        }
    }
    
    @FXML
    private void btnAnteriorAccion(){
        int indice = tablaBebida.getSelectionModel().getSelectedIndex();
        if (indice > 0) {
            tablaBebida.getSelectionModel().select(indice-1);
            getBebidaTextField();
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
        tablaBebida.setDisable(deshabilitado);
    }
    
    @FXML
    private void btnBuscarPorNombre(){
        ArrayList<Bebida> resultadoBusqueda = new ArrayList<>();
        String nombreBuscado = txtBuscar.getText();
        for(Bebida cliente:listarBebida){
            if (cliente.getNombreBebida().toLowerCase().contains(nombreBuscado.toLowerCase())) {
                resultadoBusqueda.add(cliente);
            }
        }
        tablaBebida.setItems(FXCollections.observableArrayList(resultadoBusqueda));
        if (resultadoBusqueda.isEmpty()) {
            tablaBebida.getSelectionModel().selectFirst();
        }
    }
    
    @FXML private void refrescarLIsta(ActionEvent evento) {
        txtBuscar.clear();
        tablaBebida.setItems(listarBebida);

   }
}
