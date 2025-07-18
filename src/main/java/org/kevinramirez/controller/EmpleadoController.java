
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
import org.kevinramirez.model.Empleado;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class EmpleadoController implements Initializable {

   @FXML private Button btnCerrar, btnAnterior, btnSiguiente, btnNuevo, btnEditar, btnEliminar, btnBuscar; 
    
    @FXML private TableView<Empleado> tablaEmpleado;
    @FXML private TableColumn colId, colNombre, colApellido, colCargo, colTelefono, colCorreo, colSueldo;
    @FXML private TextField txtId, txtNombre, txtApellido, txtCargo, txtTelefono, txtCorreo, txtBuscar, txtSueldo;
    
    
    private ObservableList<Empleado> listarEmpleados ;
    private Main principal; 
    private Empleado modeloEmpleado;
    private enum Acciones {AGREGAR, EDITAR, ELIMINAR, NINGUNA}
    private Acciones accion = Acciones.NINGUNA;
    
    
    public void setPrincipal(Main principal){
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setFormatoCeldasModelo(); 
        cargarDatos(); 
        
        tablaEmpleado.setOnMouseClicked(eventHandler -> getEmpleadoTextField());
        
        txtId.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtCargo.setDisable(true);
        txtTelefono.setDisable(true);
        txtCorreo.setDisable(true);
        txtSueldo.setDisable(true);
        
    }
    

    private void setFormatoCeldasModelo() {
        colId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("idEmpleado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoEmpleado"));
        colCargo.setCellValueFactory(new PropertyValueFactory<Empleado, String>("puestoEmpleado"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoEmpleado"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<Empleado, String>("correoEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldoEmpleado"));
    }    

    private void cargarDatos() {
       ArrayList<Empleado> empleados = listarEmpleado();
        listarEmpleados = FXCollections.observableArrayList(empleados);
        tablaEmpleado.setItems(listarEmpleados);
        tablaEmpleado.getSelectionModel().selectFirst();
        getEmpleadoTextField();
       
    }
    
    private void getEmpleadoTextField(){
        Empleado empleadoSeleccionado = tablaEmpleado.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
        
            txtId.setText(String.valueOf(empleadoSeleccionado.getIdEmpleado()));
            txtNombre.setText(empleadoSeleccionado.getNombreEmpleado());
            txtApellido.setText(empleadoSeleccionado.getApellidoEmpleado());
            txtCargo.setText(empleadoSeleccionado.getPuestoEmpleado());
            txtTelefono.setText(empleadoSeleccionado.getTelefonoEmpleado());
            txtCorreo.setText(empleadoSeleccionado.getCorreoEmpleado());
            txtSueldo.setText(String.valueOf(empleadoSeleccionado.getSueldoEmpleado()));

        }
    }

    private ArrayList<Empleado> listarEmpleado() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        try {
            Connection conexionv = Conexion.getInstancia().getConexion();
            String sql = "{call sp_listarEmpleados()}";
            CallableStatement enunciado = conexionv.prepareCall(sql);
            ResultSet resultado = enunciado.executeQuery();
            while (resultado.next()) {                
                empleados.add(new Empleado(
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getString(5),
                        resultado.getString(6),
                        resultado.getDouble(7)));
            }
            
        } catch (SQLException e) {
            System.out.println("error al cargar: " + e.getMessage());
        }
        return empleados;
    }
    
    private Empleado getModeloEmpleado(){
        int id;
        double sueldo; 
        
        if (txtId.getText().isEmpty()) {
            id =1;
        } else {
            id = Integer.parseInt(txtId.getText());
        }
        
        if (txtSueldo.getText().isEmpty()) {
            sueldo = 10.2; 
        } else {
            sueldo = Double.parseDouble(txtSueldo.getText()); 
        }
        
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String cargo = txtCargo.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        Empleado empleado = new Empleado(id, nombre, apellido, cargo, telefono, correo, sueldo);
        
        return empleado;
    }
    
    
    public boolean existeEmpleadoGuardar(String nombre, String apellido, String correo) {
        for (Empleado empleaddo : listarEmpleados) {
            if (empleaddo.getNombreEmpleado().equalsIgnoreCase(nombre) &&
                empleaddo.getApellidoEmpleado().equalsIgnoreCase(apellido) ||
                empleaddo.getCorreoEmpleado().equalsIgnoreCase(correo)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean existeEmpleadoEditar(String nombre, String apellido, String correo, int codigo) {
        for (Empleado empleados : listarEmpleados) {
            if (empleados.getIdEmpleado()!= codigo){
                if (empleados.getNombreEmpleado().equalsIgnoreCase(nombre) &&
                    empleados.getApellidoEmpleado().equalsIgnoreCase(apellido) ||
                    empleados.getCorreoEmpleado().equalsIgnoreCase(correo)) {
                    return true;
                }
            }    
        }
        return false;
    }
    
    public void agregarEmpleado(){
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() 
                || txtCargo.getText().isEmpty() || txtTelefono.getText().isEmpty() 
                || txtCorreo.getText().isEmpty() || txtSueldo.getText().isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Por favor, llena todos los campos");
            alert.setContentText("Debe completar todos los campos antes de agregar el Empleado");
            alert.showAndWait();
        } else if (existeEmpleadoGuardar(txtNombre.getText(), txtApellido.getText(),txtCorreo.getText())) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Duplicado");
            alerta.setHeaderText("Este empleado ya existe");
            alerta.setContentText("Ya hay un empleado con el mismo nombre y apellido o correo");
            alerta.showAndWait();
            return;
        } else {
            modeloEmpleado = getModeloEmpleado();

            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarEmpleado(?,?,?,?,?,?)}");
                enunciado.setString(1, modeloEmpleado.getNombreEmpleado());
                enunciado.setString(2, modeloEmpleado.getApellidoEmpleado());
                enunciado.setString(3, modeloEmpleado.getPuestoEmpleado());
                enunciado.setString(4, modeloEmpleado.getTelefonoEmpleado());
                enunciado.setString(5, modeloEmpleado.getCorreoEmpleado());
                enunciado.setDouble(6, modeloEmpleado.getSueldoEmpleado());
                enunciado.execute();
                cargarDatos();
                cambiarOriginal();
                habilitarDeshabilitarNodo();
                System.out.println(" Empleado agregado correctamente");
            } catch (SQLException ex) {
                System.out.println(" error al agregar Empleado: " + ex.getSQLState());
                ex.printStackTrace();
            }
        }  
    }
        
    
    public void editarEmpleado(){
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() 
                || txtCargo.getText().isEmpty() || txtTelefono.getText().isEmpty() 
                || txtCorreo.getText().isEmpty() || txtSueldo.getText().isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Por favor, llena todos los campos");
            alert.setContentText("Debe completar todos los campos para editar el Empleado");
            alert.showAndWait();
            
        } else if (existeEmpleadoEditar(txtNombre.getText(), txtApellido.getText(),
                txtCorreo.getText(), Integer.parseInt(txtId.getText()))) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Duplicado");
            alerta.setHeaderText("Este empleado ya existe");
            alerta.setContentText("Ya hay un empleado con el mismo nombre y apellido o correo");
            alerta.showAndWait();
            return;
        } else {
            modeloEmpleado = getModeloEmpleado();

            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_actualizarEmpleado(?,?,?,?,?,?,?)}");
                enunciado.setInt(1, modeloEmpleado.getIdEmpleado());
                enunciado.setString(2, modeloEmpleado.getNombreEmpleado());
                enunciado.setString(3, modeloEmpleado.getApellidoEmpleado());
                enunciado.setString(4, modeloEmpleado.getPuestoEmpleado());
                enunciado.setString(5, modeloEmpleado.getTelefonoEmpleado());
                enunciado.setString(6, modeloEmpleado.getCorreoEmpleado());
                enunciado.setDouble(7, modeloEmpleado.getSueldoEmpleado());
                enunciado.execute();
                cargarDatos();
                cambiarOriginal();
                habilitarDeshabilitarNodo();
                System.out.println(" Empleado editado correctamente");
            } catch (SQLException ex) {
                System.out.println(" error al editar Empleado: " + ex.getSQLState());
                ex.printStackTrace();
            }
        }
    }
    
    public void eliminarEmpleado(){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion de Empleado");
            alert.setHeaderText("Deceas eliminar este Empleado");
            Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK){
            modeloEmpleado = getModeloEmpleado();
            try {
                CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarEmpleado(?)}");
                enunciado.setInt(1, modeloEmpleado.getIdEmpleado());
                enunciado.execute();
                cargarDatos();
            } catch (SQLException e) {
                System.out.println(" error al eliminar " + e.getMessage());
                e.printStackTrace();
            }
        }
    }    
    
    public void limpiarTexto(){
        txtId.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCargo.clear();
        txtCorreo.clear();
        txtSueldo.clear();
    }
    
    public void btnRegresarActionEvent(ActionEvent evento){
        System.out.println("regreso a Administracion");
        principal.mostrarMenuPrincipal();
    }
    @FXML
    private void cambiarNuevoEmpleado(){
        if (null != accion) switch (accion) {
            case NINGUNA:
                cambiarGuardarEditar();
                accion = Acciones.AGREGAR;
                limpiarTexto();
                habilitarDeshabilitarNodo();
                break;
            case AGREGAR:
                System.out.println(" accion de metodo agregar ");
                agregarEmpleado();
                break;
            case EDITAR:
                System.out.println(" Accion del metodo editar");
                editarEmpleado();
                break;
            default:
                break;
        }
    }
    
    @FXML
    public void cambiarEdicionEmpleado(){
        cambiarGuardarEditar();
        accion = Acciones.EDITAR;
        habilitarDeshabilitarNodo();
    }
    
    @FXML
    public void cambiarCancelarEliminarEmpleado(){
        if (accion == Acciones.NINGUNA) {
            eliminarEmpleado();
        } else if (accion == Acciones.AGREGAR || accion == Acciones.EDITAR) {
            cambiarOriginal();
            habilitarDeshabilitarNodo();
        }
    }
    
    @FXML
    private void btnSiguienteAccion(){
        int indice = tablaEmpleado.getSelectionModel().getSelectedIndex();
        if (indice < listarEmpleados.size()-1) {
            tablaEmpleado.getSelectionModel().select(indice+1);
            getEmpleadoTextField();
        }
    }
    
    @FXML
    private void btnAnteriorAccion(){
        int indice = tablaEmpleado.getSelectionModel().getSelectedIndex();
        if (indice > 0) {
            tablaEmpleado.getSelectionModel().select(indice-1);
            getEmpleadoTextField();
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
        txtTelefono.setDisable(estado);
        txtCorreo.setDisable(estado);
        txtCargo.setDisable(estado);
        txtSueldo.setDisable(estado);
    }
    
    private void habilitarDeshabilitarNodo(){
        boolean deshabilitado = txtNombre.isDisable();
        cambiarEstado(!deshabilitado);
        btnSiguiente.setDisable(deshabilitado);
        btnAnterior.setDisable(deshabilitado);
        tablaEmpleado.setDisable(deshabilitado);
    }
    @FXML
    private void btnBuscarPorNombre(){
        //nuevo Arraylist -> obtener el texto -> usar for each del OL -> mostrar resultado
        ArrayList<Empleado> resultadoBusqueda = new ArrayList<>();
        String nombreBuscado = txtBuscar.getText();
        for(Empleado empleado:listarEmpleados){
            if (empleado.getNombreEmpleado().toLowerCase().contains(nombreBuscado.toLowerCase())) {
                resultadoBusqueda.add(empleado);
            }
        }
        tablaEmpleado.setItems(FXCollections.observableArrayList(resultadoBusqueda));
        if (resultadoBusqueda.isEmpty()) {
            tablaEmpleado.getSelectionModel().selectFirst();
        }
    }
    
    @FXML private void refrescarLIsta(ActionEvent evento) {
        txtBuscar.clear();
        tablaEmpleado.setItems(listarEmpleados);

   }
}
