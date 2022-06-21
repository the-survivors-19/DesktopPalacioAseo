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
import models.MeasurementUnitModel;

import java.io.IOException;
import java.net.HttpURLConnection;

public class MeasurementUnitController extends MenuComponent {
    @FXML
    private Button btnCreate;

    @FXML
    private TableColumn colAbbreviature;

    @FXML
    private TableColumn colActions;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colUnit;

    @FXML
    private TableView<MeasurementUnitModel> tblMeasuremenUnits;

    private ObservableList<MeasurementUnitModel> listUnits;

    @FXML
    private Button btnCloseModal;

    @FXML
    private Button btnSave;

    @FXML
    private Label lblTitleModal;

    @FXML
    private TextField txfAbbreviation;

    @FXML
    private TextField txfId;

    @FXML
    private TextField txfUnit;

    void initialize() throws Exception {
        super.initialize();
        if (tblMeasuremenUnits != null) {
            this.colId.setCellValueFactory(new PropertyValueFactory("id"));
            this.colUnit.setCellValueFactory(new PropertyValueFactory("unit"));
            this.colAbbreviature.setCellValueFactory(new PropertyValueFactory("abbreviation"));
            this.colActions.setCellValueFactory(new PropertyValueFactory("actions"));

            this.listUnits = FXCollections.observableArrayList();
            HttpURLConnection request = Http.request("/measurement-units", "", "GET");
            String response = Http.getResponse(request);
            Gson gson = new Gson();

            Schemas.MeasurementUnits[] units = gson.fromJson(response, Schemas.MeasurementUnits[].class);
            for (Schemas.MeasurementUnits unit :
                    units) {
                MeasurementUnitModel insertUnit = new MeasurementUnitModel(
                        unit.id,
                        unit.unit,
                        unit.abbreviation
                );
                this.listUnits.add(insertUnit);
            }

            this.tblMeasuremenUnits.setItems(listUnits);
            this.tblMeasuremenUnits.setOnMouseClicked(e -> {
                try {
                    this.edit();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            btnCreate.setOnMouseClicked(e -> {
                try {
                    this.openModal("measurement-units-modal", 342, 210);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (btnCloseModal != null) {
            btnSave.setOnMouseClicked(e -> {
                String dataForm = "{" +
                        "\"unit\":\"" + txfUnit.getText() + "\"," +
                        "\"abbreviation\":\"" + txfAbbreviation.getText() + "\"" +
                        "}";
                try {
                    HttpURLConnection request = Http.request("/measurement-units", dataForm, "POST");
                    if (request.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        System.out.println("Error");
                        String response = Http.getResponse(request);
                        System.out.println(response);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Crear Unidad de Medida");
                        alert.setHeaderText(null);
                        alert.setContentText("Unidad de medida creada correctamente.");
                        alert.showAndWait()
                                .ifPresent(res -> {
                                    System.out.println(res);
                                    if (res == ButtonType.OK) {
                                        Stage st = (Stage) btnCloseModal.getScene().getWindow();
                                        st.close();
                                        try {
                                            super.setScenne("measurement-units");
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
            btnCloseModal.setOnMouseClicked(e -> {
                Stage st = (Stage) btnCloseModal.getScene().getWindow();
                st.close();
            });
        }
    }

    public void edit() throws IOException {
        MeasurementUnitModel p = this.tblMeasuremenUnits.getSelectionModel().getSelectedItem();
        if (p != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("measurement-units-modal.fxml"));
            Parent root = loader.load();

            MeasurementUnitController controller = loader.getController();
            controller.initAttributes(p);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    void initAttributes(MeasurementUnitModel unit) {
        this.txfId.setText(unit.getId());
        this.txfUnit.setText(unit.getUnit());
        this.txfAbbreviation.setText(unit.getAbbreviation());
        this.lblTitleModal.setText("Editar Unidad de Medida");
        btnSave.setOnMouseClicked(e -> {
            String dataForm = "{" +
                    "\"unit\":\"" + txfUnit.getText() + "\"," +
                    "\"abbreviation\":\"" + txfAbbreviation.getText() + "\"" +
                    "}";
            try {
                HttpURLConnection request = Http.request("/measurement-units/" + this.txfId.getText(), dataForm, "PUT");
                if (request.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    System.out.println("Error");
                    String response = Http.getResponse(request);
                    System.out.println(response);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Editar Unidad de Medida");
                    alert.setHeaderText(null);
                    alert.setContentText("Unidad de medida editada correctamente.");
                    alert.showAndWait()
                            .ifPresent(res -> {
                                System.out.println(res);
                                if (res == ButtonType.OK) {
                                    Stage st = (Stage) btnCloseModal.getScene().getWindow();
                                    st.close();
                                    try {
                                        super.setScenne("measurement-units");
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
