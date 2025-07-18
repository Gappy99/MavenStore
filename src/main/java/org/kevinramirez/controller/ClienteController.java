
package org.kevinramirez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kevinramirez.database.Conexion;
import org.kevinramirez.model.Cliente;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class ClienteController implements Initializable {
    
    @FXML private Button btnRegresar, btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnBuscar;
    @FXML private TableView<Cliente> tablaCliente;
    @FXML private TableColumn colId, colNombre, colApellido, colNit, colCorreo, colPassword, colTelefono;
    @FXML private TextField txtId, txtNombre, txtApellido, txtNit, txtCorreo, txtPassword, txtTelefono, txtBuscar;
     
    private ObservableList<Cliente> listarCliente;
    private Main principal; 
    private Cliente modeloCliente;
    private enum Acciones {AGREGAR, EDITAR, ELIMINAR, NINGUNA}
    private Acciones accion = Acciones.NINGUNA;
     
    public void setPrincipal(Main principal){
        this.principal = principal;
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFormatoCeldasModelo(); 
        cargarDatos();
        tablaCliente.setOnMouseClicked(eventHandler -> getClienteTextField());
        
        txtId.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtCorreo.setDisable(true);
        txtNit.setDisable(true);
        txtPassword.setDisable(true);
        txtTelefono.setDisable(true);
    }

   
    private void setFormatoCeldasModelo() {
        
        colId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("idCliente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombreCliente"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidoCliente"));
        colNit.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nitCliente"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<Cliente, String>("correoCliente"));
        colPassword.setCellValueFactory(new PropertyValueFactory<Cliente, String>("contrasenaCliente"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefonoCliente"));
    }

    private void cargarDatos() {
      ArrayList<Cliente> clientes = ListarClientes();
        listarCliente = FXCollections.observableArrayList(clientes);
        tablaCliente.setItems(listarCliente);
        tablaCliente.getSelectionModel().selectFirst();
        getClienteTextField();
    }
    
    public void getClienteTextField(){
        Cliente clienteSeleccionado = tablaCliente.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
        
        txtId.setText(String.valueOf(clienteSeleccionado.getIdCliente()));
        txtNombre.setText(clienteSeleccionado.getNombreCliente());
        txtApellido.setText(clienteSeleccionado.getApellidoCliente());
        txtNit.setText(clienteSeleccionado.getNitCliente());
        txtCorreo.setText(clienteSeleccionado.getCorreoCliente());
        txtPassword.setText(clienteSeleccionado.getContrasenaCliente());
        txtTelefono.setText(clienteSeleccionado.getTelefonoCliente());
        
        }
    }
    
    private ArrayList<Cliente> ListarClientes () {
        ArrayList<Cliente> cliente = new ArrayList<>();
        try {
            Connection conexionv = Conexion.getInstancia().getConexion();
            String sql = "{call sp_listarClientes()}";
            CallableStatement enunciado = conexionv.prepareCall(sql);
            ResultSet resultado = enunciado.executeQuery();
            while (resultado.next()) {                
                cliente.add(new Cliente(
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getString(5),
                        resultado.getString(6),
                        resultado.getString(7)));
            }
            
        } catch (SQLException e) {
            System.out.println("error al cargar: " + e.getMessage());
        }
        return cliente;
    }
    
    private Cliente getModeloCliente(){
        int id;
        if (txtId.getText().isEmpty()) {
            id =1;
        } else {
            id = Integer.parseInt(txtId.getText());
        }
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String nit = txtNit.getText();
        String correo = txtCorreo.getText();
        String password = txtPassword.getText();
        String telefono = txtTelefono.getText();
        Cliente cliente = new Cliente(id, nombre, apellido, nit, password, password, telefono);
        
        return cliente;
    }
    public boolean existeClienteGuardar(String nombre, String apellido, String correo) {
        for (Cliente cliente : listarCliente) {
            if (cliente.getNombreCliente().equalsIgnoreCase(nombre) &&
                cliente.getApellidoCliente().equalsIgnoreCase(apellido) ||
                cliente.getCorreoCliente().equalsIgnoreCase(correo)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean existeClienteEditar(String nombre, String apellido, String correo, int codigo) {
        for (Cliente cliente : listarCliente) {
            if (cliente.getIdCliente()!= codigo){
                if (cliente.getNombreCliente().equalsIgnoreCase(nombre) &&
                    cliente.getApellidoCliente().equalsIgnoreCase(apellido) ||
                    cliente.getCorreoCliente().equalsIgnoreCase(correo)) {
                    return true;
                }
            }    
        }
        return false;
    }
    
    public void agregarCliente(){
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() 
                || txtNit.getText().isEmpty() || txtCorreo.getText().isEmpty()
                || txtPassword.getText().isEmpty()|| txtTelefono.getText().isEmpty()){
           // Crear una alerta de advertencia si falta algún campo
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Por favor, llena todos los campos");
            alert.setContentText("Debe completar todos los campos antes de agregar al Cliente");
            alert.showAndWait();
        } else if (existeClienteGuardar(txtNombre.getText(), txtApellido.getText(), txtCorreo.getText())) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Duplicado");
            alerta.setHeaderText("Este Cliente ya existe");
            alerta.setContentText("Ya hay un Cliente con el mismo nombre y apellido o correo");
            alerta.showAndWait();
            return;
        } else {
            modeloCliente = getModeloCliente();

            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarCliente(?,?,?,?,?,?)}");
                enunciado.setString(1, modeloCliente.getNombreCliente());
                enunciado.setString(2, modeloCliente.getApellidoCliente());
                enunciado.setString(3, modeloCliente.getTelefonoCliente());
                enunciado.setString(4, modeloCliente.getCorreoCliente());
                enunciado.setString(5, modeloCliente.getContrasenaCliente());
                enunciado.setString(6, modeloCliente.getTelefonoCliente());
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
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() 
                || txtNit.getText().isEmpty()|| txtCorreo.getText().isEmpty() 
                || txtPassword.getText().isEmpty()|| txtTelefono.getText().isEmpty()) {
           
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Por favor, llena todos los campos");
            alert.setContentText("Debe completar todos los campos para terminar de editar");
            alert.showAndWait();
        } else if (existeClienteEditar(txtNombre.getText(), txtApellido.getText(), 
                txtCorreo.getText(), Integer.parseInt(txtId.getText()))) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Duplicado");
            alerta.setHeaderText("Este Cliente ya existe");
            alerta.setContentText("Ya hay un Cliente con el mismo nombre y apellido o correo");
            alerta.showAndWait();
            return; 
        } else{
                modeloCliente = getModeloCliente();
            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarCliente(?,?,?,?,?,?,?)}");
                enunciado.setInt(1, modeloCliente.getIdCliente());
                enunciado.setString(2, modeloCliente.getNombreCliente());
                enunciado.setString(3, modeloCliente.getApellidoCliente());
                enunciado.setString(4, modeloCliente.getNitCliente());
                enunciado.setString(5, modeloCliente.getCorreoCliente());
                enunciado.setString(6, modeloCliente.getContrasenaCliente());
                enunciado.setString(7, modeloCliente.getTelefonoCliente());
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
            modeloCliente = getModeloCliente();
            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarCliente(?)}");
                enunciado.setInt(1, modeloCliente.getIdCliente());
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
        txtApellido.clear();
        txtCorreo.clear();
        txtNit.clear();
        txtPassword.clear();
        txtTelefono.clear();
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
                agregarCliente();
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
        int indice = tablaCliente.getSelectionModel().getSelectedIndex();
        if (indice < listarCliente.size()-1) {
            tablaCliente.getSelectionModel().select(indice+1);
            getClienteTextField();
        }
    }
    
    @FXML
    private void btnAnteriorAccion(){
        int indice = tablaCliente.getSelectionModel().getSelectedIndex();
        if (indice > 0) {
            tablaCliente.getSelectionModel().select(indice-1);
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
        txtApellido.setDisable(estado);
        txtNit.setDisable(estado);
        txtCorreo.setDisable(estado);
        txtPassword.setDisable(estado);
        txtTelefono.setDisable(estado);
    }
    
    private void habilitarDeshabilitarNodo(){
        boolean deshabilitado = txtNombre.isDisable();
        cambiarEstado(!deshabilitado);
        btnSiguiente.setDisable(deshabilitado);
        btnAnterior.setDisable(deshabilitado);
        tablaCliente.setDisable(deshabilitado);
    }
    
    @FXML
    private void btnBuscarPorNombre(){
        ArrayList<Cliente> resultadoBusqueda = new ArrayList<>();
        String nombreBuscado = txtBuscar.getText();
        for(Cliente cliente:listarCliente){
            if (cliente.getNombreCliente().toLowerCase().contains(nombreBuscado.toLowerCase())) {
                resultadoBusqueda.add(cliente);
            }
        }
        tablaCliente.setItems(FXCollections.observableArrayList(resultadoBusqueda));
        if (resultadoBusqueda.isEmpty()) {
            tablaCliente.getSelectionModel().selectFirst();
        }
    }
    
    @FXML private void refrescarLIsta(ActionEvent evento) {
        txtBuscar.clear();
        tablaCliente.setItems(listarCliente);

   }
}
