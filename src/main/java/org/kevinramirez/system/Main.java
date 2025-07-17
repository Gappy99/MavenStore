package org.kevinramirez.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kevinramirez.controller.DetallePedidoController;
import org.kevinramirez.controller.InicioController;
import org.kevinramirez.controller.LoginController;
import org.kevinramirez.controller.MenuPrincipalController;
import org.kevinramirez.controller.PedidoController;
import org.kevinramirez.controller.RegistroController;

public class Main extends Application {

   private Stage escenarioPrincipal;

    @Override
    public void start(Stage stage) {
        this.escenarioPrincipal = stage;
        mostrarDetallePedidos();
        // mostrarPedidos();
        // mostrarMenuPrincipal();
        mostrarLogin();
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

            // Comunicación con el controlador de Inicio
            MenuPrincipalController controlador = loader.getController();
            controlador.setPrincipal(this);

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
    // Agrega métodos similares para cambiar a otras vistas si lo deseas
} //clean javafx:run