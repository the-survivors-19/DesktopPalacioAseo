package com.example.aseopalacio;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductosApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProductosApplication.class.getResource("Productos.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 825, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void HomeButtonClick(ActionEvent event) {
    }
}