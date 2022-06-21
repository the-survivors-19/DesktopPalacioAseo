package com.example.aseopalacio;

import javafx.event.ActionEvent;

public class HomeLoginController extends MenuComponent{


    public void showUsers(ActionEvent event) throws Exception {

        System.out.println("mostrando usuarios");
        this.m.changeScene("users.fxml", 818, 581);
    }


}
