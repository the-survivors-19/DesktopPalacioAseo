module com.example.aseopalacio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires gson;
    requires com.fasterxml.jackson.databind;

    opens com.example.aseopalacio to javafx.fxml;
    exports com.example.aseopalacio;
    exports helpers;

    opens models to javafx.fxml;
    exports models;
}