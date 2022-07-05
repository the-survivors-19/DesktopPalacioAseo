package com.example.aseopalacio;

import com.example.aseopalacio.LoginApplication;
import com.google.gson.Gson;
import helpers.Http;
import helpers.Schemas;
import helpers.States;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.HttpURLConnection;

public class LoginController {

    public LoginController() {

    }

    @FXML
    private PasswordField password;

    @FXML
    private Button button;

    @FXML
    private TextField username;

    @FXML
    private Label wronglogin;

    public void userLogin(ActionEvent event) throws Exception {
        checkLogin();
    }

    private void checkLogin() throws Exception {


        LoginApplication m = new LoginApplication();

        String email = username.getText();
        String tePass = password.getText();

        String dataForm = "{\"email\":\"yexid.9901@gmail.com\",\"password\":\"AmHt2018Ab.\"}";
        //String dataForm = "{\"email\":\"" + email + "\",\"password\":\"" + tePass + "\"}";

        HttpURLConnection request = Http.request("/login", dataForm, "POST");

        int statusCode = request.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            wronglogin.setText("Correo o contraseña no validos");
        } else {
            String response = Http.getResponse(request);

            Gson gson = new Gson();
            States.session = gson.fromJson(response, Schemas.ResponseLogin.class);

            wronglogin.setText("Bienvenido!");
            m.changeScene("home-login.fxml", 818, 581);
        }

    }
}