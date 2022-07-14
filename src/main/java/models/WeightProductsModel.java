package models;

import com.example.aseopalacio.MenuComponent;
import helpers.Http;
import javafx.scene.control.Button;

import java.net.HttpURLConnection;

public class WeightProductsModel {
    private String id;
    private String price;
    private String stock;
    private String quantity;
    private Button actions;
    private String showQuantity;
    private String idMeasurementUnit;

    public WeightProductsModel(String id, String price, String stock, String quantity, String showQuantity, String idMeasurementUnit) {
        this.id = id;
        this.price = price;
        this.stock = stock;
        this.quantity = quantity;
        this.showQuantity = showQuantity;
        this.idMeasurementUnit = idMeasurementUnit;
        this.actions = new Button("Eliminar");
        this.actions.setOnMouseClicked(e -> {
            try {
                HttpURLConnection request = Http.request("/weight-products/" + id, "", "DELETE");
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Button getActions() {
        return actions;
    }

    public void setActions(Button actions) {
        this.actions = actions;
    }

    public String getShowQuantity() {
        return showQuantity;
    }

    public void setShowQuantity(String showQuantity) {
        this.showQuantity = showQuantity;
    }

    public String getIdMeasurementUnit() {
        return idMeasurementUnit;
    }

    public void setIdMeasurementUnit(String idMeasurementUnit) {
        this.idMeasurementUnit = idMeasurementUnit;
    }
}
