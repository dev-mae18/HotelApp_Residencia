package pe.edu.upeu.proyecto_sql.modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Persona {
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty apellido = new SimpleStringProperty();
    private final StringProperty dni = new SimpleStringProperty();

    public Persona(){}

    public Persona(String nombre, String apellido, String dni){
        setNombre(nombre);
        setApellido(apellido);
        setDni(dni);
    }
    public final String getDni() {
        return dni.get();
    }

    public final void setDni(String value) {
        dni.set(value);
    }

    public final StringProperty dniProperty() {
        return dni;
    }


    public final String getApellido() {
        return apellido.get();
    }

    public final void setApellido(String value) {
        apellido.set(value);
    }

    public final StringProperty apellidoProperty() {
        return apellido;
    }


    public final String getNombre() {
        return nombre.get();
    }

    public final void setNombre(String value) {
        nombre.set(value);
    }

    public final StringProperty nombreProperty() {
        return nombre;
    }

}
