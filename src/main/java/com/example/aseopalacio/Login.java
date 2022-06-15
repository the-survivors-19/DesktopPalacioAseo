package com.example.aseopalacio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Login {

    public Login() {

    }
    @FXML
    private PasswordField Password;

    @FXML
    private Button button;

    @FXML
    private TextField username;

    @FXML
    private Label wronglogin;



    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException{
        LoginApplication m = new LoginApplication();
        if (username.getText().equals("javacoding") && Password.getText().equals("123")){
          wronglogin.setText("Success!");

          m.changeScene("homeLogin.fxml");
     }

     else if (username.getText().isEmpty() && Password.getText().isEmpty()){
         wronglogin.setText("please enter your data.");
     }

     else {
         wronglogin.setText("wrong username or password!");
     }
  }
}