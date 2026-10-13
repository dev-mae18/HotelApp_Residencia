module pe.edu.upeu.proyecto_sql {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens pe.edu.upeu.proyecto_sql to javafx.fxml;
    //abrir el modelo para que las tablas FX funcionen
    opens pe.edu.upeu.proyecto_sql.modelo to javafx.base,javafx,fxml;
    opens pe.edu.upeu.proyecto_sql.controller to javafx.fxml;
    opens pe.edu.upeu.proyecto_sql.views to javafx.fxml;

    exports pe.edu.upeu.proyecto_sql;

    exports pe.edu.upeu.proyecto_sql.utils;
    exports pe.edu.upeu.proyecto_sql.test;
    exports pe.edu.upeu.proyecto_sql.controller;
    exports pe.edu.upeu.proyecto_sql.implementacion;
    exports pe.edu.upeu.proyecto_sql.interfaces;
}