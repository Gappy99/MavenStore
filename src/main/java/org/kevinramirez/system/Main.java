package org.kevinramirez.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage escenario) {
        try {
            // Cargar el FXML desde resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/kevinramirez/view/InicioVista.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            escenario.setTitle("Tiendita Elegante");
            escenario.setScene(scene);
            escenario.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
