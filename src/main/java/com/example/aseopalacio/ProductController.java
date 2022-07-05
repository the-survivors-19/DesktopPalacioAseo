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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.ProductModel;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ProductController extends MenuComponent {
    @FXML
    private Button btnCreate;

    @FXML
    private TableColumn colActions;

    @FXML
    private TableColumn colCategory;

    @FXML
    private TableColumn colCode;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn<ProductModel, String> colProvider;

    @FXML
    private TableColumn<ProductModel, String> colIdProduct;

    @FXML
    private TableView<ProductModel> tblProducts;

    @FXML
    private Button btnAddMeasurementProduct;

    @FXML
    private Button btnCloseModal;

    @FXML
    private ImageView btnImageFour;

    @FXML
    private ImageView btnImageOne;

    @FXML
    private ImageView btnImageThree;

    @FXML
    private ImageView btnImageTwo;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txfIdModal;

    @FXML
    private ComboBox ddCategories;

    @FXML
    private ComboBox ddProviders;

    @FXML
    private Label lblTitleModal;

    @FXML
    private TableView<?> tblMeasurementProducts;

    @FXML
    private TextArea txfDescription;

    @FXML
    private TextField txfName;

    FileChooser.ExtensionFilter filtersImages = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
    FileChooser fc = new FileChooser();
    File imgOne;
    //String base64_img1;

    private ObservableList<ProductModel> listProducts;
    private ObservableList listCategories;
    private ObservableList listProviders;
    private Map<String, String> mapCategories;
    private Map<String, String> mapProviders;

    void initialize() throws Exception {
        super.initialize();
        if(btnCloseModal != null){
            mapCategories = new HashMap();
            mapProviders = new HashMap();
            listCategories = FXCollections.observableArrayList();
            listProviders = FXCollections.observableArrayList();
            Gson gson = new Gson();

            HttpURLConnection request = Http.request("/categories", "", "GET");
            String response = Http.getResponse(request);
            Schemas.Categories[] categories = gson.fromJson(response, Schemas.Categories[].class);
            for(Schemas.Categories category :
                    categories){
                String insert = category.description;
                mapCategories.put(insert, category.id);
                listCategories.add(insert);
            }

            request = Http.request("/providers", "", "GET");
            response = Http.getResponse(request);
            Schemas.Providers[] providers = gson.fromJson(response, Schemas.Providers[].class);
            for(Schemas.Providers provider :
                    providers){
                String insert = provider.name;
                mapProviders.put(insert, provider.id);
                listProviders.add(insert);
            }

            btnCloseModal.setOnMouseClicked(e -> {
                Stage st = (Stage) btnCloseModal.getScene().getWindow();
                st.close();
            });

            this.ddCategories.setItems(listCategories);
            this.ddProviders.setItems(listProviders);

            btnImageOne.setImage(new Image(getClass().getResourceAsStream("product-default.png")));
            btnImageTwo.setImage(new Image(getClass().getResourceAsStream("product-default.png")));
            btnImageThree.setImage(new Image(getClass().getResourceAsStream("product-default.png")));
            btnImageFour.setImage(new Image(getClass().getResourceAsStream("product-default.png")));
            btnImageOne.setOnMouseClicked(e -> {
                Stage st = (Stage) btnCloseModal.getScene().getWindow();
                fc.getExtensionFilters().add(filtersImages);
                imgOne = fc.showOpenDialog(st);


                //base64_img1 = Base64.getEncoder().encodeToString();
                System.out.println("img: "+imgOne.getAbsoluteFile());

                Image img1 = new Image(imgOne.toURI().toString());
                btnImageOne.setImage(img1);
            });
            btnSave.setOnMouseClicked(e -> {
                String providerDD = this.ddProviders.getValue().toString();
                String categoryDD = this.ddCategories.getValue().toString();
                String providerId = mapProviders.get(providerDD);
                String categoryId = mapCategories.get(categoryDD);
                String dataForm = "{" +
                        "\"name\":\"" + txfName.getText() + "\"," +
                        "\"description\":\"" + txfDescription.getText() + "\"," +
                        "\"category_id\":\"" + categoryId + "\"," +
                        "\"provider_id\":\"" + providerId + "\"" +
                        "}";
                try {
                    HttpURLConnection saveProduct = Http.request("/products", dataForm, "POST");
                    if (saveProduct.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        System.out.println("Error");
                        String resProduct = Http.getResponse(saveProduct);
                        System.out.println(resProduct);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Crear Producto");
                        alert.setHeaderText(null);
                        alert.setContentText("Producto creado correctamente.");
                        alert.showAndWait()
                                .ifPresent(res -> {
                                    System.out.println(res);
                                    if (res == ButtonType.OK) {
                                        Stage st = (Stage) btnCloseModal.getScene().getWindow();
                                        st.close();
                                        try {
                                            super.setScenne("products");
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
        if(tblProducts != null){
            this.colIdProduct.setCellValueFactory(new PropertyValueFactory("id"));
            this.colName.setCellValueFactory(new PropertyValueFactory("name"));
            this.colCode.setCellValueFactory(new PropertyValueFactory("code"));
            this.colCategory.setCellValueFactory(new PropertyValueFactory("category_id"));
            this.colProvider.setCellValueFactory(new PropertyValueFactory("provider_id"));
            this.colActions.setCellValueFactory(new PropertyValueFactory("actions"));
            this.listProducts = FXCollections.observableArrayList();
            HttpURLConnection request = Http.request("/products", "", "GET");
            String response = Http.getResponse(request);
            Gson gson = new Gson();

            Schemas.Products[] products = gson.fromJson(response, Schemas.Products[].class);
            for (Schemas.Products product :
                    products) {
                ProductModel insertUnit = new ProductModel(
                        product.id,
                        product.code,
                        product.name,
                        product.description,
                        product.category_id.get("description"),
                        product.provider_id.get("name")
                );
                this.listProducts.add(insertUnit);
            }

            this.tblProducts.setItems(listProducts);

            this.tblProducts.setOnMouseClicked(e -> {
                try {
                    this.edit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            btnCreate.setOnMouseClicked(e -> {
                try {
                    this.openModal("products-modal", 754, 467);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    void edit() throws Exception {
        ProductModel p = this.tblProducts.getSelectionModel().getSelectedItem();
        if (p != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("products-modal.fxml"));
            Parent root = loader.load();

            ProductController controller = loader.getController();
            controller.initAttributes(p);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    void initAttributes(ProductModel product) throws Exception {
        ddCategories.setValue(product.getCategory_id());
        ddProviders.setValue(product.getProvider_id());
        this.txfIdModal.setText(product.getId());
        this.txfName.setText(product.getName());
        this.txfDescription.setText(product.getDescription());
        this.lblTitleModal.setText("Editar Producto");
        btnSave.setOnMouseClicked(e -> {
            String providerDD = this.ddProviders.getValue().toString();
            String categoryDD = this.ddCategories.getValue().toString();
            String providerId = mapProviders.get(providerDD);
            String categoryId = mapCategories.get(categoryDD);
            String dataForm = "{" +
                    "\"name\":\"" + txfName.getText() + "\"," +
                    "\"description\":\"" + txfDescription.getText() + "\"," +
                    "\"category_id\":\"" + categoryId + "\"," +
                    "\"provider_id\":\"" + providerId + "\"" +
                    "}";
            try {
                HttpURLConnection request = Http.request("/products/" + this.txfIdModal.getText(), dataForm, "PUT");
                if (request.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    System.out.println("Error");
                    String response = Http.getResponse(request);
                    System.out.println(response);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Editar Producto");
                    alert.setHeaderText(null);
                    alert.setContentText("Producto editado correctamente.");
                    alert.showAndWait()
                            .ifPresent(res -> {
                                if (res == ButtonType.OK) {
                                    Stage st = (Stage) btnCloseModal.getScene().getWindow();
                                    st.close();
                                    try {
                                        super.setScenne("products");
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
