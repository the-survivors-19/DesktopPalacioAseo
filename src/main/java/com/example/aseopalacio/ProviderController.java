package com.example.aseopalacio;

import com.google.gson.Gson;
import helpers.Http;
import helpers.Schemas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.ProviderModel;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ProviderController extends MenuComponent {
    @FXML
    private Button btnCreateProvider;

    @FXML
    private TableColumn colActions;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colDutyManager;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPhone;

    @FXML
    private TableColumn colId;

    @FXML
    private TableView<ProviderModel> tblProviders;

    @FXML
    private Button btnCloseModal;

    @FXML
    private Button btnSave;

    @FXML
    private Label lblTitlteModal;

    @FXML
    private TextField txfAddress;

    @FXML
    private TextField txfDutyManager;

    @FXML
    private TextField txfEmail;

    @FXML
    private TextField txfName;

    @FXML
    private TextField txfPhone;

    @FXML
    private TextField txfId;

    private ObservableList<ProviderModel> listProviders;

    void initialize() throws Exception {
        super.initialize();
        if (btnCreateProvider != null) {
            btnCreateProvider.setOnMouseClicked(e -> {
                try {
                    this.openModal("providers-modal", 321, 398);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if(btnCloseModal != null){
            btnCloseModal.setOnMouseClicked(e -> {
                Stage st = (Stage) btnCloseModal.getScene().getWindow();
                st.close();
            });
        }
        if (tblProviders != null) {
            this.colId.setCellValueFactory(new PropertyValueFactory("id"));
            this.colName.setCellValueFactory(new PropertyValueFactory("name"));
            this.colAddress.setCellValueFactory(new PropertyValueFactory("address"));
            this.colDutyManager.setCellValueFactory(new PropertyValueFactory("duty_manager"));
            this.colEmail.setCellValueFactory(new PropertyValueFactory("email"));
            this.colPhone.setCellValueFactory(new PropertyValueFactory("phone"));
            this.colActions.setCellValueFactory(new PropertyValueFactory("actions"));

            this.listProviders = FXCollections.observableArrayList();
            HttpURLConnection request = Http.request("/providers", "", "GET");
            String response = Http.getResponse(request);
            Gson gson = new Gson();

            Schemas.Providers[] providers = gson.fromJson(response, Schemas.Providers[].class);
            for (Schemas.Providers provider :
                    providers) {
                ProviderModel insertCategory = new ProviderModel(
                        provider.id,
                        provider.name,
                        provider.phone,
                        provider.address,
                        provider.duty_manager,
                        provider.email
                );
                this.listProviders.add(insertCategory);
            }

            this.tblProviders.setItems(listProviders);
            this.tblProviders.setOnMouseClicked(e -> {
                try {
                    this.editProvider();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if(btnSave != null){
            System.out.println("crado");
            btnSave.setOnMouseClicked(e -> {
                String dataForm = "{" +
                        "\"name\":\"" + txfName.getText() + "\"," +
                        "\"phone\":\"" + txfPhone.getText() + "\"," +
                        "\"address\":\"" + txfAddress.getText() + "\"," +
                        "\"duty_manager\":\"" + txfDutyManager.getText() + "\"," +
                        "\"email\":\"" + txfEmail.getText() + "\"" +
                        "}";
                try {
                    HttpURLConnection request = Http.request("/providers", dataForm, "POST");
                    String response = Http.getResponse(request);
                    System.out.println(response);
                    if (request.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        System.out.println("Error");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Creación de proveedor");
                        alert.setHeaderText(null);
                        alert.setContentText("Proveedor creado correctamente.");
                        alert.showAndWait()
                                .ifPresent(res -> {
                                    System.out.println(res);
                                    if (res == ButtonType.OK) {
                                        Stage st = (Stage) btnCloseModal.getScene().getWindow();
                                        st.close();
                                        try {
                                            super.setScenne("providers");
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });
                        System.out.println("Creado");

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    public void editProvider() throws IOException {
        ProviderModel p = this.tblProviders.getSelectionModel().getSelectedItem();
        if (p != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("providers-modal.fxml"));
            Parent root = loader.load();

            ProviderController controller = loader.getController();
            controller.initAttributes(p);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    void initAttributes(ProviderModel provider) {
        this.txfId.setText(provider.getId());
        this.txfEmail.setText(provider.getEmail());
        this.txfName.setText(provider.getName());
        this.txfPhone.setText(provider.getPhone());
        this.txfAddress.setText(provider.getAddress());
        this.txfDutyManager.setText(provider.getDuty_manager());
        this.lblTitlteModal.setText("Editar Categoria");
        btnSave.setOnMouseClicked(e -> {
            System.out.println("Evento de editar");
            String dataForm = "{" +
                    "\"name\":\"" + txfName.getText() + "\"," +
                    "\"phone\":\"" + txfPhone.getText() + "\"," +
                    "\"address\":\"" + txfAddress.getText() + "\"," +
                    "\"duty_manager\":\"" + txfDutyManager.getText() + "\"," +
                    "\"email\":\"" + txfEmail.getText() + "\"" +
                    "}";
            try {
                HttpURLConnection request = Http.request("/providers/" + this.txfId.getText(), dataForm, "PUT");
                if (request.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    System.out.println("Error");
                    String response = Http.getResponse(request);
                    System.out.println(response);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Editar Proveedor");
                    alert.setHeaderText(null);
                    alert.setContentText("Proveedor editado correctamente correctamente.");
                    alert.showAndWait()
                            .ifPresent(res -> {
                                System.out.println(res);
                                if (res == ButtonType.OK) {
                                    Stage st = (Stage) btnCloseModal.getScene().getWindow();
                                    st.close();
                                    try {
                                        super.setScenne("providers");
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
