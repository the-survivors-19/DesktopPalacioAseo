package com.example.aseopalacio;

import helpers.Functions;
import helpers.Http;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;

public class UserController extends MenuComponent {
    @FXML
    private Button btnCreateUser;

    @FXML
    private Button btnSaveUser;

    @FXML
    private Button btnCloseModalCreate;

    @FXML
    private TextField txfAddress;

    @FXML
    private TextField txfEmail;

    @FXML
    private TextField txfFullName;

    @FXML
    private TextField txfPhone;

    void initialize() {
        if (btnCreateUser != null) {
            btnCreateUser.setOnMouseClicked(e -> {
                try {
                    this.openModal("create-user", 282, 531);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (btnSaveUser != null) {
            btnSaveUser.setOnMouseClicked(e -> {
                String password = Functions.generatePassword();
                String dataForm = "{" +
                        "\"full_name\":\"" + txfFullName.getText() + "\"," +
                        "\"phone\":\"" + txfPhone.getText() + "\"," +
                        "\"address\":\"" + txfAddress.getText() + "\"," +
                        "\"email\":\"" + txfEmail.getText() + "\"," +
                        "\"password\":\"" + password + "\"," +
                        "\"password_confirmation\":\"" + password + "\"" +
                        "}";
                System.out.println(dataForm);
                try {
                    HttpURLConnection request = Http.request("/users", dataForm, "POST");
                    String response = Http.getResponse(request);
                    System.out.println(response);
                    if (request.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        System.out.println("Error");
                    } else {
                        System.out.println("Creado");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (btnCloseModalCreate != null) {
            btnCloseModalCreate.setOnMouseClicked(e -> {
                Stage st = (Stage) btnCloseModalCreate.getScene().getWindow();
                st.close();
            });
        }
    }
}
