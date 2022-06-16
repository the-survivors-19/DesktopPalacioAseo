package com.example.aseopalacio;

import helpers.States;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeLoginController extends MenuComponent{


    public void showUsers(ActionEvent event) throws Exception {

        System.out.println("mostrando usuarios");
        this.m.changeScene("users.fxml");
    }


}
