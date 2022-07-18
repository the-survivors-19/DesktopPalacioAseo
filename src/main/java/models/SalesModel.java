package models;

import com.example.aseopalacio.SalesManagerController;
import helpers.Schemas;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class SalesModel {
    private String id;
    private String date;
    private String total;
    private Button currentState;
    private Map<String, String> user_id;
    private String name_client;
    private String address;
    private List<Schemas.SalesDetails> salesDetails;
    private String state;

    public SalesModel(String id, String date, String total, String current_state, Map<String, String> user_id, String name_client, String address) {
        this.id = id;
        this.date = date;
        this.state = current_state;
        this.total = total;
        this.currentState = new Button(current_state);
        this.currentState.setOnMouseClicked(e -> {
            try {
                URL locationModal = SalesManagerController.class.getResource("sale-change-state.fxml");
                FXMLLoader loader = new FXMLLoader(locationModal);
                Parent root = (Parent) loader.load();

                SalesManagerController controller = loader.getController();
                controller.initAttributesState(this.id, this.state);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        this.user_id = user_id;
        this.name_client = name_client;
        this.address = address;
    }

    public List<Schemas.SalesDetails> getSalesDetails() {
        return salesDetails;
    }

    public void setSalesDetails(List<Schemas.SalesDetails> salesDetails) {
        this.salesDetails = salesDetails;
    }

    public Button getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Button currentState) {
        this.currentState = currentState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Map<String, String> getUser_id() {
        return user_id;
    }

    public void setUser_id(Map<String, String> user_id) {
        this.user_id = user_id;
    }

    public String getName_client() {
        return name_client;
    }

    public void setName_client(String name_client) {
        this.name_client = name_client;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
