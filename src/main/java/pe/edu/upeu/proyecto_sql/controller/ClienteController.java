package pe.edu.upeu.proyecto_sql.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import pe.edu.upeu.proyecto_sql.implementacion.ClienteDaoImpl;
import pe.edu.upeu.proyecto_sql.interfaces.IClienteDao;
import pe.edu.upeu.proyecto_sql.modelo.Cliente;
import pe.edu.upeu.proyecto_sql.utils.UtilAlerts;

import java.util.Optional;
import java.io.IOException;

public class ClienteController {

    @FXML
    private TableView<Cliente> tableCliente;

    @FXML
    private TableColumn<Cliente, Number> ColNumero;

    @FXML
    private TableColumn<Cliente, Integer> ColId;

    @FXML
    private TableColumn<Cliente, String> ColNombres;

    @FXML
    private TableColumn<Cliente, String> ColApellidos;

    @FXML
    private TableColumn<Cliente, String> ColDni;

    @FXML
    private TableColumn<Cliente, String> ColEmail;

    @FXML
    private TableColumn<Cliente, String> ColGenero;


    // Variables generales

    private final IClienteDao clienteDao = new ClienteDaoImpl();

    private ObservableList<Cliente> listaClientes;


    @FXML
    public void initialize() {

        // Columna número
        ColNumero.setCellFactory(
                (TableColumn<Cliente, Number> colum) -> new TableCell<>() {

                    @Override
                    protected void updateItem(Number item, boolean empty) {

                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            setText(String.valueOf(getIndex() + 1));
                        }
                    }
                }
        );


        // Columna ID
        ColId.setCellValueFactory(
                cellData -> cellData.getValue()
                        .idclienteProperty()
                        .asObject()
        );


        // Columna Nombres
        ColNombres.setCellValueFactory(
                cellData -> cellData.getValue()
                        .nombreProperty()
        );


        // Columna Apellidos
        ColApellidos.setCellValueFactory(
                cellData -> cellData.getValue()
                        .apellidoProperty()
        );


        // Columna DNI
        ColDni.setCellValueFactory(
                cellData -> cellData.getValue()
                        .dniProperty()
        );


        // Columna Email
        ColEmail.setCellValueFactory(
                cellData -> cellData.getValue()
                        .emailProperty()
        );


        // Columna Género
        ColGenero.setCellValueFactory(
                cellData -> cellData.getValue()
                        .generoProperty()
        );


        // Cargar datos
        cargarDatos();
    }


    private void cargarDatos() {

        listaClientes = FXCollections.observableArrayList(
                clienteDao.listAll()
        );

        tableCliente.setItems(listaClientes);
    }

    @FXML
    private void handleDelete() {
        Cliente selectId = tableCliente.getSelectionModel().getSelectedItem();

        if (selectId != null) {
            // Usamos UtilAlerts con sus 3 parámetros (tipo CONFIRMATION)
            Alert confirm = UtilAlerts.mostrarAlerta(
                    "Confirmar eliminación",
                    "¿Estás seguro de eliminar el registro " + selectId.getNombre() + "?",
                    "CONFIRMATION"
            );

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (clienteDao.delete(selectId)) {
                    listaClientes.remove(selectId);
                }
            }

        } else {
            // Este else SÍ corresponde al if (selectId != null)
            UtilAlerts.mostrarAlerta(
                    "Selección requerida",
                    "Seleccione un cliente para eliminar",
                    "WARNING"
            ).showAndWait();
        }
    }

    @FXML
    private void handleNew() {
        abrirFormulario(null);
    }

    @FXML
    private void handleEdit() {
        Cliente seleccionado = tableCliente.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Seleccion requerida", "Seleccione un cliente para editar.");
            return;
        }
        try {
            Cliente completo = clienteDao.searchById(seleccionado.getIdcliente());
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

    private void abrirFormulario(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/pe/edu/upeu/proyecto_sql/views/cliente-form.fxml"));
            Parent root = loader.load();
            ClientFormController controller = loader.getController();
            controller.setCliente(cliente);

            Stage ventana = new Stage();
            ventana.setTitle(cliente == null ? "Nuevo cliente" : "Editar cliente");
            ventana.initOwner(tableCliente.getScene().getWindow());
            ventana.initModality(Modality.WINDOW_MODAL);
            ventana.setScene(new Scene(root));
            ventana.setResizable(false);
            ventana.showAndWait();

            if (controller.isSave()) {
                Cliente procesado = controller.getCliente();
                boolean guardado;
                try {
                    guardado = cliente == null
                            ? clienteDao.insert(procesado)
                            : clienteDao.update(procesado);
                } catch (RuntimeException ex) {
                    guardado = false;
                    System.out.println("Error al guardar cliente: " + ex.getMessage());
                }
                if (!guardado) {
                    UtilAlerts.mostrarAlerta("Error al guardar",
                            "No se pudo guardar el registro. Revise la conexion y los datos ingresados.",
                            "ERROR").showAndWait();
                }
                cargarDatos();
            }
        } catch (IOException ex) {
            UtilAlerts.mostrarAlerta("Error", "No se pudo abrir el formulario de clientes.",
                    "ERROR").showAndWait();
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
