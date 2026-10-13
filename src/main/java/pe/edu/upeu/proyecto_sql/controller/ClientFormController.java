package pe.edu.upeu.proyecto_sql.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import pe.edu.upeu.proyecto_sql.modelo.Cliente;
import pe.edu.upeu.proyecto_sql.utils.UtilAlerts;

public class ClientFormController {

    // ===== Campos FXML =====
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtDni;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cbxGenero;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    // ===== Estado =====
    private boolean save = false;
    private Cliente cliente;

    // ===== Inicialización =====
    @FXML
    public void initialize() {
        cbxGenero.getItems().addAll("M", "F");
    }

    // ===== Métodos públicos para el controller padre =====
    public boolean isSave() {
        return save;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente c) {
        save = false;
        this.cliente = c == null ? null : new Cliente(c.getNombre(), c.getApellido(),
                c.getDni(), c.getIdcliente(), c.getEmail(), c.getGenero());
        if (c != null) {
            txtNombre.setText(c.getNombre());
            txtApellidos.setText(c.getApellido());
            txtDni.setText(c.getDni());
            txtEmail.setText(c.getEmail());
            cbxGenero.setValue(c.getGenero());
        }
    }

    // ===== Validación básica =====
    private boolean validate() {
        if (txtNombre.getText().isBlank()
                || txtApellidos.getText().isBlank()
                || txtDni.getText().isBlank()
                || txtEmail.getText().isBlank()
                || cbxGenero.getValue() == null) {

            UtilAlerts.mostrarAlerta(
                    "Validación",
                    "Debe completar todos los campos.",
                    "WARNING"
            ).showAndWait();

            return false;
        }
        return true;
    }

    // ===== Acciones de botones =====
    @FXML
    private void handleSave() {
        if (!validate()) return;

        if (cliente == null) {
            cliente = new Cliente();
        }

        cliente.setNombre(txtNombre.getText().trim());
        cliente.setApellido(txtApellidos.getText().trim());
        cliente.setDni(txtDni.getText().trim());
        cliente.setEmail(txtEmail.getText().trim());
        cliente.setGenero(cbxGenero.getValue());

        save = true;
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        save = false;
        closeWindow();
    }

    // ===== Cerrar ventana =====
    private void closeWindow() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
