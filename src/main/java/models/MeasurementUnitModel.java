package models;

import com.example.aseopalacio.MenuComponent;
import helpers.Http;
import javafx.scene.control.Button;

import java.net.HttpURLConnection;

public class MeasurementUnitModel {
    private String id;
    private String unit;
    private String abbreviation;
    private Button actions;

    public MeasurementUnitModel(String id, String unit, String abbreviation) {
        this.id = id;
        this.unit = unit;
        this.abbreviation = abbreviation;
        this.actions = new Button("Eliminar");
        this.actions.setOnMouseClicked(e -> {
            try {
                HttpURLConnection request = Http.request("/measurement-units/" + id, "", "DELETE");
                String response = Http.getResponse(request);
                MenuComponent mc = new MenuComponent();
                mc.setScenne("measurement-units");
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Button getActions() {
        return actions;
    }

    public void setActions(Button actions) {
        this.actions = actions;
    }
}
