package pe.edu.upeu.proyecto_sql.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pe.edu.upeu.proyecto_sql.modelo.Habitacion;
import pe.edu.upeu.proyecto_sql.utils.UtilAlerts;

public class HabitacionFormController {
    @FXML private TextField txtNumero;
    @FXML private TextField txtNumeroPiso;
    @FXML private TextField txtNumeroHabitacion;
    @FXML private TextField txtTipo;
    @FXML private TextField txtPrecio;
    @FXML private ComboBox<String> cbxEstado;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;
    private Habitacion habitacion;
    private boolean save = false;

    @FXML
    public void initialize() {
        cbxEstado.getItems().addAll("Disponible", "Ocupada", "Mantenimiento");
        cbxEstado.setEditable(true);
        cbxEstado.setValue("Disponible");
    }

    public boolean isSave() {
        return save;
    }
    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion h) {
        save = false;
        this.habitacion = h == null ? null : new Habitacion(h.getIdhabitacion(),
                h.getNumero(), h.getNumeroPiso(), h.getNumeroHabitacion(),
                h.getTipo(), h.getPrecio(), h.getEstado());
        if (h != null) {
            txtNumero.setText(h.getNumero());
            txtNumeroPiso.setText(h.getNumeroPiso());
            txtNumeroHabitacion.setText(h.getNumeroHabitacion());
            txtTipo.setText(h.getTipo());
            txtPrecio.setText(Double.toString(h.getPrecio()));
            cbxEstado.setValue(h.getEstado());
        }
    }

    // Validacion basica
    private boolean validate() {
        if (txtNumero.getText().isBlank() || txtTipo.getText().isBlank()
                || txtPrecio.getText().isBlank()) {
            UtilAlerts.mostrarAlerta("Validacion", "Complete numero, tipo y precio.", "WARNING").showAndWait();
            return false;
        }
        double precio;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim().replace(',', '.'));
            if (!Double.isFinite(precio) || precio < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            UtilAlerts.mostrarAlerta("Validacion", "Ingrese un precio numerico mayor o igual a cero.", "WARNING").showAndWait();
            return false;
        }
        return true;
    }

    // Acciones de botones
    @FXML
    private void handleSave() {
        if (!validate()) return;

        if (habitacion == null) {
            habitacion = new Habitacion();
        }
        habitacion.setNumero(txtNumero.getText().trim());
        habitacion.setNumeroPiso(txtNumeroPiso.getText().trim());
        habitacion.setNumeroHabitacion(txtNumeroHabitacion.getText().trim());
        habitacion.setTipo(txtTipo.getText().trim());
        habitacion.setPrecio(Double.parseDouble(txtPrecio.getText().trim().replace(',', '.')));
        String estado = cbxEstado.getEditor().getText();
        habitacion.setEstado(estado == null || estado.isBlank() ? "Disponible" : estado.trim());
        save = true;
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        save = false;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
