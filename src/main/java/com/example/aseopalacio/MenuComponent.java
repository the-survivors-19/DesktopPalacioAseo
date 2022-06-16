package com.example.aseopalacio;

import helpers.States;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuComponent {
    LoginApplication m = new LoginApplication();

    /* MENU */
    @FXML
    private Button btnCategorias;
    @FXML
    private Button btnCerrarSesion;
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
    @FXML
    private Label messageLogin;


    /* FUNCTIONS */
    @FXML
    void initialize() throws Exception {
        System.out.println("entra");
        btnUsuario.setOnMouseClicked(e -> {
            try {
                setScenne("users");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnCategorias.setOnMouseClicked(e -> {
            try {
                setScenne("categories");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnClientes.setOnMouseClicked(e -> {
            try {
                setScenne("customers");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnInventario.setOnMouseClicked(e -> {
            try {
                setScenne("inventary");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnPrivilegios.setOnMouseClicked(e -> {
            try {
                setScenne("privileges");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnProductos.setOnMouseClicked(e -> {
            try {
                setScenne("products");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnReportes.setOnMouseClicked(e -> {
            try {
                setScenne("reports");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnRoles.setOnMouseClicked(e -> {
            try {
                setScenne("roles");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnVentas.setOnMouseClicked(e -> {
            try {
                setScenne("sales");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnCerrarSesion.setOnMouseClicked(e -> {
            try {
                userLogOut(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        String[] name = States.session.user.get("full_name").split(" ");
        String msg = "Bienvenido " + name[0].substring(0, 1).toUpperCase() + name[0].substring(1).toLowerCase() + ".";
        messageLogin.setText(msg);
    }

    public void userLogOut(MouseEvent event) throws IOException {
        States.logout();
        this.m.changeScene("login-view.fxml");
    }

    public void setScenne(String scenne) throws Exception {
        this.m.changeScene(scenne+".fxml");
    }

    public void openModal(String modal, int width, int heigth) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(modal+".fxml")));
        Stage stage = new Stage();
        stage.setResizable(true);
        stage.setTitle(modal);
        stage.setScene(new Scene(root, width, heigth));
        stage.show();
    }
}
