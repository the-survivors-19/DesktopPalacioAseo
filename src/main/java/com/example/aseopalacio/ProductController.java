package com.example.aseopalacio;

import com.google.gson.Gson;
import helpers.Http;
import helpers.MultipartUtility;
import helpers.Schemas;
import helpers.States;
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
import models.WeightProductsModel;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
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
    private TableView<WeightProductsModel> tblWeightProducts;

    @FXML
    private TextArea txfDescription;

    @FXML
    private TextField txfName;

    @FXML
    private TableColumn<WeightProductsModel, String> colWeightProductActions;

    @FXML
    private TableColumn<WeightProductsModel, String> colWeightProductPrice;

    @FXML
    private TableColumn<WeightProductsModel, String> colWeightProductQuantity;

    @FXML
    private TableColumn<?, ?> colWeightProductId;

    @FXML
    private TableColumn<?, ?> colWeightProductIdDdMU;

    @FXML
    private TableColumn<?, ?> colWeightProductShowQuantity;

    @FXML
    private TableColumn<?, ?> colWeightProductStock;

    @FXML
    private Button btnWeightProductsCloseModal;

    @FXML
    private Button btnWeightProductsSave;

    @FXML
    private ComboBox ddWeightProductsMeasurementProduct;

    @FXML
    private Label lblTitleModalWeightProducts;

    @FXML
    private TextField txfWeightProductsIdProduct;

    @FXML
    private TextField txfWeightProductsId;

    @FXML
    private TextField txfWeightProductsPrice;

    @FXML
    private TextField txfWeightProductsQuantity;

    @FXML
    private TextField txfWeightProductsStock;

    @FXML
    private Label lblMeasurementUnit;

    FileChooser.ExtensionFilter filtersImages = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg");
    FileChooser fc = new FileChooser();
    File imgOne;
    File imgTwo;
    File imgThree;
    File imgFour;

    private ObservableList<ProductModel> listProducts;
    private ObservableList<WeightProductsModel> listWeightProducts;
    private ObservableList listCategories;
    private ObservableList listProviders;
    private ObservableList listMeasurementUnits;
    private Map<String, String> mapCategories;
    private Map<String, String> mapProviders;
    private Map<String, String> mapMeasurementUnits;

    void initialize() throws Exception {
        super.initialize();
        if (btnWeightProductsCloseModal != null) {
            btnWeightProductsCloseModal.setOnMouseClicked(e -> {
                Stage st = (Stage) btnWeightProductsCloseModal.getScene().getWindow();
                st.close();
            });

            HttpURLConnection request = Http.request("/measurement-units", "", "GET");
            String response = Http.getResponse(request);
            mapMeasurementUnits = new HashMap();
            listMeasurementUnits = FXCollections.observableArrayList();

            Gson gson = new Gson();

            Schemas.MeasurementUnits[] measurementUnits = gson.fromJson(response, Schemas.MeasurementUnits[].class);

            for (Schemas.MeasurementUnits measurementUnit :
                    measurementUnits) {
                String insert = measurementUnit.unit;
                mapMeasurementUnits.put(insert, measurementUnit.id);
                listMeasurementUnits.add(insert);
            }

            this.ddWeightProductsMeasurementProduct.setItems(listMeasurementUnits);

            btnWeightProductsSave.setOnMouseClicked(e -> {
                String muIdDD = this.ddWeightProductsMeasurementProduct.getValue().toString();
                String measurementUnitId = mapMeasurementUnits.get(muIdDD);
                String dataForm = "{" +
                        "\"quantity\":" + txfWeightProductsQuantity.getText() + "," +
                        "\"stock\":" + txfWeightProductsStock.getText() + "," +
                        "\"price\":" + txfWeightProductsPrice.getText() + "," +
                        "\"product_id\":\"" + txfWeightProductsIdProduct.getText() + "\"," +
                        "\"measurement_unit_id\":\"" + measurementUnitId + "\"" +
                        "}";
                try {
                    HttpURLConnection saveProduct = Http.request("/weight-products", dataForm, "POST");
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
                                        Stage st = (Stage) btnWeightProductsSave.getScene().getWindow();
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
        if (btnCloseModal != null) {
            mapCategories = new HashMap();
            mapProviders = new HashMap();
            listCategories = FXCollections.observableArrayList();
            listProviders = FXCollections.observableArrayList();
            Gson gson = new Gson();

            HttpURLConnection request = Http.request("/categories", "", "GET");
            String response = Http.getResponse(request);
            Schemas.Categories[] categories = gson.fromJson(response, Schemas.Categories[].class);
            for (Schemas.Categories category :
                    categories) {
                String insert = category.description;
                mapCategories.put(insert, category.id);
                listCategories.add(insert);
            }

            request = Http.request("/providers", "", "GET");
            response = Http.getResponse(request);
            Schemas.Providers[] providers = gson.fromJson(response, Schemas.Providers[].class);
            for (Schemas.Providers provider :
                    providers) {
                String insert = provider.name;
                mapProviders.put(insert, provider.id);
                listProviders.add(insert);
            }

            this.tblWeightProducts.setOnMouseClicked(e -> {
                try {
                    this.editWeightProducts();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

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
                System.out.println("img: " + imgOne.getAbsoluteFile());

                Image img1 = new Image(imgOne.toURI().toString());
                btnImageOne.setImage(img1);
            });
            btnImageTwo.setOnMouseClicked(e -> {
                Stage st = (Stage) btnCloseModal.getScene().getWindow();
                fc.getExtensionFilters().add(filtersImages);
                imgTwo = fc.showOpenDialog(st);


                //base64_img1 = Base64.getEncoder().encodeToString();
                System.out.println("img: " + imgTwo.getAbsoluteFile());

                Image img = new Image(imgTwo.toURI().toString());
                btnImageTwo.setImage(img);
            });

            btnImageThree.setOnMouseClicked(e -> {
                Stage st = (Stage) btnCloseModal.getScene().getWindow();
                fc.getExtensionFilters().add(filtersImages);
                imgThree = fc.showOpenDialog(st);


                //base64_img1 = Base64.getEncoder().encodeToString();
                System.out.println("img: " + imgThree.getAbsoluteFile());

                Image img = new Image(imgThree.toURI().toString());
                btnImageThree.setImage(img);
            });

            btnImageFour.setOnMouseClicked(e -> {
                Stage st = (Stage) btnCloseModal.getScene().getWindow();
                fc.getExtensionFilters().add(filtersImages);
                imgFour = fc.showOpenDialog(st);


                //base64_img1 = Base64.getEncoder().encodeToString();
                System.out.println("img: " + imgFour.getAbsoluteFile());

                Image img = new Image(imgFour.toURI().toString());
                btnImageFour.setImage(img);
            });

            btnSave.setOnMouseClicked(e -> {
                String providerDD = this.ddProviders.getValue().toString();
                String categoryDD = this.ddCategories.getValue().toString();
                String providerId = mapProviders.get(providerDD);
                String categoryId = mapCategories.get(categoryDD);
                try {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
                    MultipartUtility multipart = new MultipartUtility("/products", "UTF-8", headers);
                    System.out.println("token: "+States.session.token);
                    multipart.addFormField("name", txfName.getText() );
                    multipart.addFormField("description", txfDescription.getText());
                    multipart.addFormField("category_id", categoryId);
                    multipart.addFormField("provider_id", providerId);
                    if(imgOne != null) {
                        multipart.addFilePart("images", imgOne);
                    }
                    if(imgTwo != null) {
                        multipart.addFilePart("images", imgTwo);
                    }
                    if(imgThree != null) {
                        multipart.addFilePart("images", imgThree);
                    }
                    if(imgFour != null) {
                        multipart.addFilePart("images", imgFour);
                    }
                    String resMultipart = multipart.finish();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Producto");
                    alert.setHeaderText(null);
                    alert.setContentText(resMultipart);
                    alert.showAndWait()
                            .ifPresent(res -> {
                                System.out.println(res);
                                if (res == ButtonType.OK) {
                                    Stage st = (Stage) btnSave.getScene().getWindow();
                                    st.close();
                                    try {
                                        this.setScenne("products");
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (tblProducts != null) {
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
                        product.provider_id.get("name"),
                        product.img_1,
                        product.img_2,
                        product.img_3,
                        product.img_4
                );
                insertUnit.setWeight_products(product.weight_products);
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

    void editWeightProducts() throws Exception {
        WeightProductsModel p = this.tblWeightProducts.getSelectionModel().getSelectedItem();
        if (p != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("products-weight-add-modal.fxml"));
            Parent root = loader.load();

            ProductController controller = loader.getController();
            controller.initAttributesWeightProducts(p, txfIdModal.getText());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
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

    void initAttributesWeightProducts(WeightProductsModel product, String idProduct) {
        ddWeightProductsMeasurementProduct.setValue(product.getIdMeasurementUnit());
        txfWeightProductsId.setText(product.getId());
        txfWeightProductsIdProduct.setText(idProduct);
        txfWeightProductsPrice.setText(product.getPrice());
        txfWeightProductsStock.setText(product.getStock());
        txfWeightProductsQuantity.setText(product.getQuantity());

        btnWeightProductsSave.setOnMouseClicked(e -> {
            String muIdDD = this.ddWeightProductsMeasurementProduct.getValue().toString();
            String measurementUnitId = mapMeasurementUnits.get(muIdDD);
            String dataForm = "{" +
                    "\"quantity\":" + txfWeightProductsQuantity.getText() + "," +
                    "\"stock\":" + txfWeightProductsStock.getText() + "," +
                    "\"price\":" + txfWeightProductsPrice.getText() + "," +
                    "\"product_id\":\"" + txfWeightProductsIdProduct.getText() + "\"," +
                    "\"measurement_unit_id\":\"" + measurementUnitId + "\"" +
                    "}";
            try {
                HttpURLConnection saveProduct = Http.request("/weight-products/" + txfWeightProductsId.getText(), dataForm, "PUT");
                if (saveProduct.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    System.out.println("Error");
                    String resProduct = Http.getResponse(saveProduct);
                    System.out.println(resProduct);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Editar Producto");
                    alert.setHeaderText(null);
                    alert.setContentText("Producto editado correctamente.");
                    alert.showAndWait()
                            .ifPresent(res -> {
                                System.out.println(res);
                                if (res == ButtonType.OK) {
                                    Stage st = (Stage) btnWeightProductsCloseModal.getScene().getWindow();
                                    st.close();
                                    System.out.println(tblWeightProducts);
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

    void initAttributesWeightProductsAdd(String id) {
        txfWeightProductsIdProduct.setText(id);
    }

    void initAttributes(ProductModel product) {
        this.tblWeightProducts.setVisible(true);
        this.btnAddMeasurementProduct.setVisible(true);
        this.lblMeasurementUnit.setVisible(true);
        ddCategories.setValue(product.getCategory_id());
        ddProviders.setValue(product.getProvider_id());
        this.txfIdModal.setText(product.getId());
        this.txfName.setText(product.getName());

        boolean haveImage;

        haveImage = product.getImg_1() == null || product.getImg_1().equals("");
        if(!haveImage){
            this.btnImageOne.setImage(new Image(product.getImg_1()));
        }

        haveImage = product.getImg_2() == null || product.getImg_2().equals("");
        if(!haveImage){
            this.btnImageTwo.setImage(new Image(product.getImg_2()));
        }

        haveImage = product.getImg_3() == null || product.getImg_3().equals("");
        if(!haveImage){
            this.btnImageThree.setImage(new Image(product.getImg_3()));
        }

        haveImage = product.getImg_4() == null || product.getImg_4().equals("");
        if(!haveImage){
            this.btnImageFour.setImage(new Image(product.getImg_4()));
        }
        this.txfDescription.setText(product.getDescription());
        this.lblTitleModal.setText("Editar Producto");
        this.colWeightProductShowQuantity.setCellValueFactory(new PropertyValueFactory("showQuantity"));
        this.colWeightProductPrice.setCellValueFactory(new PropertyValueFactory("price"));
        this.colWeightProductActions.setCellValueFactory(new PropertyValueFactory("actions"));
        this.listWeightProducts = FXCollections.observableArrayList();
        for (Schemas.WeightProduct productAdd :
                product.getWeight_products()) {
            if (productAdd.remove.equals("false")) {
                WeightProductsModel weightProductsModel = new WeightProductsModel(
                        productAdd.id,
                        productAdd.price,
                        productAdd.stock,
                        productAdd.quantity,
                        productAdd.quantity + productAdd.measurement_unit_id.get("abbreviation"),
                        productAdd.measurement_unit_id.get("unit")
                );
                this.listWeightProducts.add(weightProductsModel);

            }
        }

        this.tblWeightProducts.setItems(listWeightProducts);

        btnAddMeasurementProduct.setOnMouseClicked(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("products-weight-add-modal.fxml"));
                Parent root = loader.load();

                ProductController controller = loader.getController();
                controller.initAttributesWeightProductsAdd(txfIdModal.getText());

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnSave.setOnMouseClicked(e -> {
            String providerDD = this.ddProviders.getValue().toString();
            String categoryDD = this.ddCategories.getValue().toString();
            String providerId = mapProviders.get(providerDD);
            String categoryId = mapCategories.get(categoryDD);
            try {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
                MultipartUtility multipart = new MultipartUtility("/products/"+txfIdModal.getText(), "UTF-8", headers);
                System.out.println("token: "+States.session.token);
                multipart.addFormField("name", txfName.getText() );
                multipart.addFormField("description", txfDescription.getText());
                multipart.addFormField("category_id", categoryId);
                multipart.addFormField("provider_id", providerId);
                if(imgOne != null) {
                    multipart.addFilePart("images", imgOne);
                }
                if(imgTwo != null) {
                    multipart.addFilePart("images", imgTwo);
                }
                if(imgThree != null) {
                    multipart.addFilePart("images", imgThree);
                }
                if(imgFour != null) {
                    multipart.addFilePart("images", imgFour);
                }
                String resMultipart = multipart.finish();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Producto");
                alert.setHeaderText(null);
                alert.setContentText(resMultipart);
                alert.showAndWait()
                        .ifPresent(res -> {
                            System.out.println(res);
                            if (res == ButtonType.OK) {
                                Stage st = (Stage) btnSave.getScene().getWindow();
                                st.close();
                                try {
                                    this.setScenne("products");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                System.out.println(resMultipart);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }
}