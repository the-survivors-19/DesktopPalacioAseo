package com.example.aseopalacio;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Pane slider;

    @FXML
    private Label MenuBack;

    @FXML
    private Label MenuClose;

    @FXML
    private Button btnCategorias;

    @FXML
    private Button btnCerrarSesión;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnInventario;

    @FXML
    private Button btnPrivilegios;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnReportes;

    @FXML
    private Button btnRoles;

    @FXML
    private Button btnUsuario;

    @FXML
    private Button btnVentas;



    public HomeController() {
    }


    @Override
    public void initialize(URL location, ResourceBundle resource) {

    }
 }
