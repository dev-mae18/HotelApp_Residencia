package pe.edu.upeu.proyecto_sql.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pe.edu.upeu.proyecto_sql.modelo.Habitacion;
import pe.edu.upeu.proyecto_sql.interfaces.IHabitacionDao;
import pe.edu.upeu.proyecto_sql.implementacion.HabitacionDaoImpl;
import pe.edu.upeu.proyecto_sql.utils.UtilAlerts;
import java.io.IOException;

public class HabitacionController {
    @FXML
    private TableView<Habitacion> tableHabitacion;
    @FXML
    private TableColumn<Habitacion, Number> ColOrden;
    @FXML
    private TableColumn<Habitacion, Integer> ColId;
    @FXML
    private TableColumn<Habitacion, String> ColNumero;
    @FXML
    private TableColumn<Habitacion, String> ColNumeroPiso;
    @FXML
    private TableColumn<Habitacion, String> ColNumeroHabitacion;
    @FXML
    private TableColumn<Habitacion, String> ColTipo;
    @FXML
    private TableColumn<Habitacion, Number> ColPrecio;
    @FXML
    private TableColumn<Habitacion, String> ColEstado;
    private final IHabitacionDao habitacionDao = new HabitacionDaoImpl();

    private ObservableList<Habitacion> listaHabitaciones;

    @FXML
    public void initialize() {
        ColOrden.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1));
            }
        });
        ColId.setCellValueFactory(cellData -> cellData.getValue().idhabitacionProperty().asObject());
        ColNumero.setCellValueFactory(cellData -> cellData.getValue().numeroProperty());
        ColNumeroPiso.setCellValueFactory(cellData -> cellData.getValue().numeroPisoProperty());
        ColNumeroHabitacion.setCellValueFactory(cellData -> cellData.getValue().numeroHabitacionProperty());
        ColTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        ColPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty());
        ColEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        cargarDatos();
    }

    private void cargarDatos() {
        listaHabitaciones = FXCollections.observableArrayList(
                habitacionDao.listAll()
        );
        tableHabitacion.setItems(listaHabitaciones);
    }

    @FXML
    private void handleNew() {
        abrirFormulario(null);
    }

    @FXML
    private void handleEdit() {
        Habitacion seleccionado = tableHabitacion.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccion requerida", "Seleccione una habitacion para editar.");
            return;
        }
        try {
            Habitacion completo = habitacionDao.searchById(seleccionado.getIdhabitacion());
            if (completo == null) {
                mostrarAlerta("Registro no disponible", "No se pudo recuperar el registro seleccionado.");
                cargarDatos();
                return;
            }
            abrirFormulario(completo);
        } catch (RuntimeException ex) {
            UtilAlerts.mostrarAlerta("Error", "No se pudo consultar el registro por ID.",
                    "ERROR").showAndWait();
        }
    }

    @FXML
    private void handleDelete() {
        Habitacion selectId = tableHabitacion.getSelectionModel().getSelectedItem();
        if (selectId == null) {
            mostrarAlerta("Seleccion requerida", "Seleccione una habitacion para eliminar.");
            return;
        }
        Alert confirm = UtilAlerts.mostrarAlerta("Confirmar eliminacion",
                "¿Desea eliminar la habitacion " + selectId.getNumero() + "?", "CONFIRMATION");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;
        if (habitacionDao.delete(selectId)) {
            listaHabitaciones.remove(selectId);
        }
    }

    private void abrirFormulario(Habitacion habitacion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/pe/edu/upeu/proyecto_sql/views/habitacion-form.fxml"));
            Parent root = loader.load();
            HabitacionFormController controller = loader.getController();
            controller.setHabitacion(habitacion);
            Stage ventana = new Stage();
            ventana.setTitle(habitacion == null ? "Nueva habitacion" : "Editar habitacion");
            ventana.initOwner(tableHabitacion.getScene().getWindow());
            ventana.initModality(Modality.WINDOW_MODAL);
            ventana.setScene(new Scene(root));
            ventana.setResizable(false);
            ventana.showAndWait();
            if (controller.isSave()) {
                Habitacion procesado = controller.getHabitacion();
                boolean guardado;
                try {
                    guardado = habitacion == null
                            ? habitacionDao.insert(procesado)
                            : habitacionDao.update(procesado);
                } catch (RuntimeException ex) {
                    guardado = false;
                    System.out.println("Error al guardar habitacion: " + ex.getMessage());
                }
                if (!guardado) {
                    UtilAlerts.mostrarAlerta("Error al guardar",
                            "No se pudo guardar el registro. Revise la conexion y los datos ingresados.",
                            "ERROR").showAndWait();
                }
                cargarDatos();
            }
        } catch (IOException ex) {
            UtilAlerts.mostrarAlerta("Error", "No se pudo abrir el formulario de habitaciones.", "ERROR").showAndWait();
            ex.printStackTrace();
        }
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
