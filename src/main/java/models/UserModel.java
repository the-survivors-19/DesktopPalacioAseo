package models;

import com.example.aseopalacio.MenuComponent;
import helpers.Http;
import helpers.States;
import javafx.scene.control.Button;

import java.net.HttpURLConnection;

public class UserModel {
    private String id;
    private String full_name;
    private String phone;
    private String address;
    private String email;
    private Button actions;

    public UserModel(String id, String fullName, String phone, String address, String email) {
        this.id = id;
        this.full_name = fullName;
        this.phone = phone;
        this.address = address;
        this.email = email;
        if(!States.session.user.get("id").equals(id+"")) {
            this.actions = new Button("Eliminar");
            this.actions.setOnMouseClicked(e -> {
                try {
                    HttpURLConnection request = Http.request("/users/" + id, "", "DELETE");
                    String response = Http.getResponse(request);
                    MenuComponent mc = new MenuComponent();
                    mc.setScenne("users");
                    System.out.println(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Button getActions() {
        return actions;
    }

    public void setActions(Button actions) {
        this.actions = actions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
