package pe.edu.upeu.proyecto_sql.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane contenArea;

    @FXML
    public void initialize() {
        showHome();
    }

    @FXML
    private void showHome() {
        loadViews("home-view.fxml");
    }

    @FXML
    private void showClients() {
        loadViews("cliente-view.fxml");
    }

    @FXML
    private void showHabitaciones() {
        loadViews("habitacion-view.fxml");
    }

    private void loadViews(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/upeu/proyecto_sql/views/"+fxml));
            Parent view = loader.load();
            contenArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error de carga de vista."+e.getMessage());
        }
    }
}
