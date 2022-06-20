package com.example.aseopalacio;

import helpers.States;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
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
        if (this.btnUsuario != null) {
            this.btnUsuario.setOnMouseClicked(e -> {
                try {
                    setScenne("users");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (this.btnCategorias != null) {
            this.btnCategorias.setOnMouseClicked(e -> {
                try {
                    setScenne("categories");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (this.btnClientes != null) {
            this.btnClientes.setOnMouseClicked(e -> {
                try {
                    setScenne("customers");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (this.btnInventario != null) {
            this.btnInventario.setOnMouseClicked(e -> {
                try {
                    setScenne("inventary");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (this.btnPrivilegios != null) {
            this.btnPrivilegios.setOnMouseClicked(e -> {
                try {
                    setScenne("privileges");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (this.btnProductos != null) {
            this.btnProductos.setOnMouseClicked(e -> {
                try {
                    setScenne("products");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (this.btnReportes != null) {
            this.btnReportes.setOnMouseClicked(e -> {
                try {
                    setScenne("reports");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (this.btnRoles != null) {
            this.btnRoles.setOnMouseClicked(e -> {
                try {
                    setScenne("roles");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (this.btnVentas != null) {
            this.btnVentas.setOnMouseClicked(e -> {
                try {
                    setScenne("sales");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (this.btnCerrarSesion != null) {
            this.btnCerrarSesion.setOnMouseClicked(e -> {
                try {
                    userLogOut(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (this.messageLogin != null) {
            String[] name = States.session.user.get("full_name").split(" ");
            String msg = "Bienvenido " + name[0].substring(0, 1).toUpperCase() + name[0].substring(1).toLowerCase() + ".";
            this.messageLogin.setText(msg);
        }

    }

    public void userLogOut(MouseEvent event) throws IOException {
        States.logout();
        this.m.changeScene("login-view.fxml");
    }

    public void setScenne(String scenne, Map<String, String>... data) throws Exception {
        this.m.changeScene(scenne + ".fxml");
    }

    public void openModal(String modal, int width, int heigth) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(modal + ".fxml")));
        Stage stage = new Stage();
        stage.setResizable(true);
        stage.setTitle(modal);
        stage.setScene(new Scene(root, width, heigth));
        stage.show();
    }
}
