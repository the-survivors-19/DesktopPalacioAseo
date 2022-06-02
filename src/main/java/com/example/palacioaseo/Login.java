package com.example.palacioaseo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

@SuppressWarnings("EqualsReplaceableByObjectsCall")
public class Login {
    public Login() {
    }

    @FXML
    private Button Ingresar;
    @FXML
    private Label wronglogin;
    @FXML
    private TextField correo;
    @FXML
    private PasswordField contraseña;


    public void userLogin(ActionEvent event) throws IOException{
        checkLogin();
    }

    private void checkLogin() throws IOException {
        Main m =  new Main();
        if (correo.getText().toString().equals("java-coding") && contraseña.getText().toString().equals("123")){
            wronglogin.setText("Success!");

            m.changeScene("after-login.fxml");

        }
        else if (correo.getText().isEmpty() && contraseña.getText().isEmpty()){
            wronglogin.setText("please enter your data.");
        }
        else {
            wronglogin.setText("Wrong username o password!");
        }
    }
}
