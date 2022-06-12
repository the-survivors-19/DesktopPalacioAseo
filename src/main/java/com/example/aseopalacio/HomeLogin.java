package com.example.aseopalacio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

@SuppressWarnings("unused")
public class HomeLogin {

    @FXML
    private Button logout;

    public void userLogOut(ActionEvent event) throws IOException{
        LoginApplication m = new LoginApplication();
        m.changeScene("login-view.fxml");
    }

    public void userLogin(ActionEvent event) {
    }
}
