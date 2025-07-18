package org.kevinramirez.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import org.kevinramirez.database.Conexion;
import org.kevinramirez.model.Bebida;
import org.kevinramirez.model.Cliente;
import org.kevinramirez.model.Comida;
import org.kevinramirez.model.Empleado;
import org.kevinramirez.system.Main;

/**
 * FXML Controller class
 *
 * @author edy14
 */
public class MenuPrincipalController implements Initializable {

    @FXML
    private ListView<Comida> listComidas;
    @FXML
    private ListView<Bebida> listBebidas;
    @FXML
    private ListView<String> listCarrito;
    @FXML
    private ComboBox<Empleado> cmbEmpleado;

    @FXML
    private RadioButton rbEfectivo, rbTarjeta;
    
    @FXML
    private Label lblTipo;
    @FXML
    private Label lblPrecio;
    @FXML
    private Label lblTotal;

    private ObservableList<Empleado> listaEmpleados;

    private ObservableList<Comida> comidas;
    private ObservableList<Bebida> bebidas;
    private ObservableList<String> carrito;

    private double total = 0.0;

    private Cliente clienteActual;
    private Empleado empleadoAsignado;
    private Main principal;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    public void setClienteActual(Cliente cliente) {
        this.clienteActual = cliente;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comidas = FXCollections.observableArrayList();
        bebidas = FXCollections.observableArrayList();
        carrito = FXCollections.observableArrayList();

        listComidas.setItems(comidas);
        listBebidas.setItems(bebidas);
        listCarrito.setItems(carrito);

        cargarComidas();
        cargarBebidas();
        cargarEmpleados();

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

    private void cargarEmpleados() {
        listaEmpleados = FXCollections.observableArrayList();

        try {
            Connection conn = Conexion.getInstancia().getConexion();
            CallableStatement stmt = conn.prepareCall("{call sp_listarEmpleados()}");
            ResultSet lstEmpleados = stmt.executeQuery();

            while (lstEmpleados.next()) {
                Empleado empleados = new Empleado(
                        lstEmpleados.getInt("ID"),
                        lstEmpleados.getString("NOMBRE"),
                        lstEmpleados.getString("APELLIDO"));

                listaEmpleados.add(empleados);
            }

            cmbEmpleado.setItems(listaEmpleados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        Empleado empleadoSeleccionado = cmbEmpleado.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado == null || clienteActual == null) {
            return;
        }

        try {
            Connection conn = Conexion.getInstancia().getConexion();
            
            CallableStatement stmt = conn.prepareCall("{call sp_agregarPedido(?,?,?,?,?)}");
            stmt.setInt(1, clienteActual.getIdCliente());
            stmt.setInt(2, empleadoSeleccionado.getIdEmpleado());
            stmt.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis())); 
            String metodoPago = rbEfectivo.isSelected() ? "Efectivo" : "Tarjeta";
            stmt.setString(4, metodoPago);
            stmt.setString(5, "Pagado");

            stmt.execute();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(idPedido) FROM Pedidos");
            int idPedidoGenerado = -1;
            if (rs.next()) {
                idPedidoGenerado = rs.getInt(1);
            }
            rs.close();
            st.close();

            if (idPedidoGenerado == -1) {
                throw new SQLException("No se pudo obtener el ID del pedido generado.");
            }
            
            for (String item : carrito) {
                int idComida = 0;
                int idBebida = 0;
                int cantidad = 1; 

                boolean esComida = item.startsWith("Comida:");

                if (esComida) {
                    for (Comida c : comidas) {
                        if (item.contains(c.getNombreComida())) {
                            idComida = c.getIdComida();
                            break;
                        }
                    }
                } else {
                    for (Bebida b : bebidas) {
                        if (item.contains(b.getNombreBebida())) {
                            idBebida = b.getIdBebida();
                            break;
                        }
                    }
                }

                if (idComida != 0 || idBebida != 0) {
                    CallableStatement detalleStmt = conn.prepareCall("{call sp_agregarDetallePedido(?,?,?,?)}");
                    detalleStmt.setInt(1, idPedidoGenerado);

                    if (idComida != 0) {
                        detalleStmt.setInt(2, idComida);
                    } else {
                        detalleStmt.setNull(2, java.sql.Types.INTEGER);
                    }

                    if (idBebida != 0) {
                        detalleStmt.setInt(3, idBebida);
                    } else {
                        detalleStmt.setNull(3, java.sql.Types.INTEGER);
                    }

                    detalleStmt.setInt(4, cantidad);
                    detalleStmt.execute();
                    detalleStmt.close();
                }
            }
            System.out.println("Pedido registrado correctamente.");
            carrito.clear();
            total = 0;
            lblTotal.setText("$0.00");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
