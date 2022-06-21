package com.example.aseopalacio;

import com.google.gson.Gson;
import helpers.Functions;
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
import models.CategoryModel;

import java.io.IOException;
import java.net.HttpURLConnection;

public class CategoryController extends MenuComponent {

    @FXML
    private TableColumn colActions;

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colId;

    @FXML
    private Button btnCreateCategory;

    @FXML
    private Button btnCloseModal;

    @FXML
    private Button btnSaveCategory;

    @FXML
    private Label lblTitleModal;

    @FXML
    private TextField txfDescription;

    @FXML
    private TableView<CategoryModel> tblCategories;

    @FXML
    private TextField txfId;

    private ObservableList<CategoryModel> listCategories;

    void initialize() throws Exception {
        super.initialize();

        if(btnCreateCategory != null){
            btnCreateCategory.setOnMouseClicked(e -> {
                try {
                    this.openModal("categories-modal", 349, 150);
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
        if(btnSaveCategory != null){
            btnSaveCategory.setOnMouseClicked(e -> {
                String dataForm = "{" +
                        "\"description\":\"" + txfDescription.getText() + "\"" +
                        "}";
                System.out.println(dataForm);
                try {
                    HttpURLConnection request = Http.request("/categories", dataForm, "POST");
                    String response = Http.getResponse(request);
                    System.out.println(response);
                    if (request.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        System.out.println("Error");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Creación de categoria");
                        alert.setHeaderText(null);
                        alert.setContentText("Categoria creada correctamente.");
                        alert.showAndWait()
                                .ifPresent(res -> {
                                    System.out.println(res);
                                    if (res == ButtonType.OK) {
                                        Stage st = (Stage) btnCloseModal.getScene().getWindow();
                                        st.close();
                                        try {
                                            super.setScenne("categories");
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
        if(txfDescription != null){

        }

        if(tblCategories != null){
            this.colId.setCellValueFactory(new PropertyValueFactory("id"));
            this.colDescription.setCellValueFactory(new PropertyValueFactory("description"));
            this.colActions.setCellValueFactory(new PropertyValueFactory("actions"));

            this.listCategories = FXCollections.observableArrayList();
            HttpURLConnection request = Http.request("/categories", "", "GET");
            String response = Http.getResponse(request);
            Gson gson = new Gson();

            Schemas.Categories[] categories = gson.fromJson(response, Schemas.Categories[].class);
            for (Schemas.Categories categorie :
                    categories) {
                CategoryModel insertCategory = new CategoryModel(
                        categorie.id,
                        categorie.description
                );
                this.listCategories.add(insertCategory);
            }

            this.tblCategories.setItems(listCategories);
            this.tblCategories.setOnMouseClicked(e -> {
                try {
                    this.editCategory();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    public void editCategory() throws IOException {
        CategoryModel p = this.tblCategories.getSelectionModel().getSelectedItem();
        if (p != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("categories-modal.fxml"));
            Parent root = loader.load();

            CategoryController controller = loader.getController();
            controller.initAttributes(p);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    void initAttributes(CategoryModel category) {
        this.txfDescription.setText(category.getDescription());
        this.txfId.setText(category.getId());
        this.lblTitleModal.setText("Editar Categoria");
        btnSaveCategory.setOnMouseClicked(e -> {
            String dataForm = "{" +
                    "\"description\":\"" + txfDescription.getText() + "\"" +
                    "}";
            try {
                HttpURLConnection request = Http.request("/categories/"+this.txfId.getText(), dataForm, "PUT");
                if (request.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    System.out.println("Error");
                    String response = Http.getResponse(request);
                    System.out.println(response);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Editar categoria");
                    alert.setHeaderText(null);
                    alert.setContentText("Categoria editada correctamente.");
                    alert.showAndWait()
                            .ifPresent(res -> {
                                System.out.println(res);
                                if (res == ButtonType.OK) {
                                    Stage st = (Stage) btnCloseModal.getScene().getWindow();
                                    st.close();
                                    try {
                                        super.setScenne("categories");
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
