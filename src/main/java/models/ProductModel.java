package models;

import com.example.aseopalacio.MenuComponent;
import helpers.Http;
import javafx.scene.control.Button;

import java.net.HttpURLConnection;
import java.util.Map;

public class ProductModel {
    private String id;
    private String name;
    private String code;
    private String description;
    private String category_id;
    private String provider_id;
    private Button actions;

    public ProductModel(String id, String code, String name, String description, String category_id, String provider_id) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.category_id = category_id;
        this.provider_id = provider_id;
        this.actions = new Button("Eliminar");
        this.actions.setOnMouseClicked(e -> {
            try {
                HttpURLConnection request = Http.request("/products/" + id, "", "DELETE");
                String response = Http.getResponse(request);
                MenuComponent mc = new MenuComponent();
                mc.setScenne("products");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public Button getActions() {
        return actions;
    }

    public void setActions(Button actions) {
        this.actions = actions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
