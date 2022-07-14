package com.example.aseopalacio;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class SalesManager extends MenuComponent {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colIdProduct;

    @FXML
    private TableColumn<?, ?> colNameClient;

    @FXML
    private TableColumn<?, ?> colState;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Label messageLogin;

    @FXML
    private Pane slider;

    @FXML
    private TableView<?> tblSales;

    void initialize() throws Exception {
        super.initialize();
    }

}

