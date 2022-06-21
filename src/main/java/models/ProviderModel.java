package models;

import com.example.aseopalacio.MenuComponent;
import helpers.Http;
import javafx.scene.control.Button;

import java.net.HttpURLConnection;

public class ProviderModel {
    private String id;
    private String name;
    private String phone;
    private String address;
    private String duty_manager;
    private String email;
    private Button actions;

    public ProviderModel(String id, String name, String phone, String address, String duty_manager, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.duty_manager = duty_manager;
        this.email = email;
        this.actions = new Button("Eliminar");
        this.actions.setOnMouseClicked(e -> {
            try {
                HttpURLConnection request = Http.request("/providers/" + id, "", "DELETE");
                String response = Http.getResponse(request);
                MenuComponent mc = new MenuComponent();
                mc.setScenne("providers");
                System.out.println(response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDuty_manager() {
        return duty_manager;
    }

    public void setDuty_manager(String duty_manager) {
        this.duty_manager = duty_manager;
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
}
