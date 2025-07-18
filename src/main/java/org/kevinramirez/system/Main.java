package org.kevinramirez.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kevinramirez.controller.BebidaController;
import org.kevinramirez.controller.ClienteController;
import org.kevinramirez.controller.ComidaController;
import org.kevinramirez.controller.DetallePedidoController;
import org.kevinramirez.controller.EmpleadoController;
import org.kevinramirez.controller.InicioController;
import org.kevinramirez.controller.LoginController;
import org.kevinramirez.controller.MenuPrincipalController;
import org.kevinramirez.controller.PedidoController;
import org.kevinramirez.controller.RegistroController;
import org.kevinramirez.model.Cliente;

public class Main extends Application {

   private Stage escenarioPrincipal;
   
   private Cliente clienteActual;

    
    public void setClienteActual(Cliente cliente) {
        this.clienteActual = cliente;
    }

    public Cliente getClienteActual() {
        return this.clienteActual;
    }

    @Override
    public void start(Stage stage) {
        this.escenarioPrincipal = stage;
        mostrarBebida();
        //mostrarComida();
        //mostrarClientes();
        // mostrarDetallePedidos();
        // mostrarEmpleados();
        // mostrarPedidos();
        // mostrarMenuPrincipal();
        // mostrarLogin();
    }
    
    
    public void mostrarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Scene escena = new Scene(loader.load());
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Login");
            escenarioPrincipal.show();

            // Comunicación con el controlador, si es necesario
            LoginController controlador = loader.getController();
            controlador.setPrincipal(this);

        } catch (Exception e) {
            System.err.println("Error al cargar la vista LoginView.fxml:");
            e.printStackTrace();
        }
    }
    
    public void mostrarInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InicioView.fxml"));
            Scene escena = new Scene(loader.load());
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Inisio sesion");
            escenarioPrincipal.show();

            // Comunicación con el controlador de Inicio
            InicioController controlador = loader.getController();
            controlador.setPrincipal(this);

        } catch (Exception e) {
            System.err.println("Error al cargar la vista InicioView.fxml:");
            e.printStackTrace();
        }
    }
    
    public void mostrarRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegistroView.fxml"));
            Scene escena = new Scene(loader.load());
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Registrarse ");
            escenarioPrincipal.show();

            // Comunicación con el controlador de Inicio
            RegistroController controlador = loader.getController();
            controlador.setPrincipal(this);

        } catch (Exception e) {
            System.err.println("Error al cargar la vista RegistroView.fxml:");
            e.printStackTrace();
        }
    
    }
    
    public void mostrarMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuPrincipalView.fxml"));
            Scene escena = new Scene(loader.load());
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Registrarse ");
            escenarioPrincipal.show();
            
            MenuPrincipalController controlador = loader.getController();
            controlador.setPrincipal(this);
            controlador.setClienteActual(clienteActual);

        } catch (Exception e) {
            System.err.println("Error al cargar la vista RegistroView.fxml:");
            e.printStackTrace();
        }
    }
        
    public void mostrarPedidos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PedidoView.fxml"));
            Scene escena = new Scene(loader.load());
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Registrarse ");
            escenarioPrincipal.show();

            // Comunicación con el controlador de Inicio
            PedidoController controlador = loader.getController();
            controlador.setPrincipal(this);

        } catch (Exception e) {
            System.err.println("Error al cargar la vista PedidoView.fxml:");
            e.printStackTrace();
        }
    }
    
    public void mostrarDetallePedidos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetallePedidoView.fxml"));
            Scene escena = new Scene(loader.load());
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Registrarse ");
            escenarioPrincipal.show();

            // Comunicación con el controlador de Inicio
            DetallePedidoController controlador = loader.getController();
            controlador.setPrincipal(this);

        } catch (Exception e) {
            System.err.println("Error al cargar la vista DetallePedidosView.fxml:");
            e.printStackTrace();
        }
    }
    
     public void mostrarEmpleados() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmpleadoView.fxml"));
            Scene escena = new Scene(loader.load());
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Registrarse ");
            escenarioPrincipal.show();

            // Comunicación con el controlador de Inicio
            EmpleadoController controlador = loader.getController();
            controlador.setPrincipal(this);

        } catch (Exception e) {
            System.err.println("Error al cargar la vista EmpleadoView.fxml:");
            e.printStackTrace();
        }
    }
     
     public void mostrarClientes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ClienteView.fxml"));
            Scene escena = new Scene(loader.load());
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Registrarse ");
            escenarioPrincipal.show();

            ClienteController controlador = loader.getController();
            controlador.setPrincipal(this);

        } catch (Exception e) {
            System.err.println("Error al cargar la vista ClienteView.fxml:");
            e.printStackTrace();
        }
    }
     
     public void mostrarComida() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ComidaView.fxml"));
            Scene escena = new Scene(loader.load());
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Registrarse ");
            escenarioPrincipal.show();

            ComidaController controlador = loader.getController();
            controlador.setPrincipal(this);

        } catch (Exception e) {
            System.err.println("Error al cargar la vista ComidaView.fxml:");
            e.printStackTrace();
        }
    }
     
     public void mostrarBebida() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BebidaView.fxml"));
            Scene escena = new Scene(loader.load());
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setTitle("Registrarse ");
            escenarioPrincipal.show();

            BebidaController controlador = loader.getController();
            controlador.setPrincipal(this);

        } catch (Exception e) {
            System.err.println("Error al cargar la vista ComidaView.fxml:");
            e.printStackTrace();
        }
    }
    // Agrega métodos similares para cambiar a otras vistas si lo deseas
} //clean javafx:run