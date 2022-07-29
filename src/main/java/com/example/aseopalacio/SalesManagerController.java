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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.MeasurementUnitModel;
import models.ProductModel;
import models.SalesModel;

import java.net.HttpURLConnection;

public class SalesManagerController extends MenuComponent {

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colNameClient;

    @FXML
    private TableColumn colState;

    @FXML
    private TableColumn colTotal;

    @FXML
    private TableView<SalesModel> tblSales;

    @FXML
    private Button btnCloseModal;

    @FXML
    private TextArea txfRequest;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblNameClient;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblIdReq;

    private ObservableList<SalesModel> listSales;

    @FXML
    private Button btnCloseModalState;

    @FXML
    private Button btnUpdateState;

    @FXML
    private ComboBox ddStates;

    @FXML
    private Label lblIdCode;

    void initialize() throws Exception {
        super.initialize();
        if(this.tblSales != null) {
            this.colAddress.setCellValueFactory(new PropertyValueFactory("address"));
            this.colDate.setCellValueFactory(new PropertyValueFactory("date"));
            this.colState.setCellValueFactory(new PropertyValueFactory("currentState"));
            this.colTotal.setCellValueFactory(new PropertyValueFactory("total"));
            this.colNameClient.setCellValueFactory(new PropertyValueFactory("name_client"));
            this.listSales = FXCollections.observableArrayList();
            HttpURLConnection request = Http.request("/sales", "", "GET");
            String response = Http.getResponse(request);
            Gson gson = new Gson();
            Schemas.Sales[] sales = gson.fromJson(response, Schemas.Sales[].class);
            for (Schemas.Sales sale :
                    sales) {
                SalesModel insertSale = new SalesModel(
                        sale.id,
                        sale.date,
                        sale.total,
                        sale.state,
                        sale.user_id,
                        sale.name_client,
                        sale.address
                );
                insertSale.setSalesDetails(sale.sales_details);
                this.listSales.add(insertSale);
            }

            this.tblSales.setItems(listSales);
            this.tblSales.setOnMouseClicked(e -> {
                try {
                    this.edit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        if(this.btnCloseModal != null){
            btnCloseModal.setOnMouseClicked(e -> {
                Stage st = (Stage) btnCloseModal.getScene().getWindow();
                st.close();
            });
        }
    }


    void edit() throws Exception {
        SalesModel p = this.tblSales.getSelectionModel().getSelectedItem();
        if (p != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-sale.fxml"));
            Parent root = loader.load();

            SalesManagerController controller = loader.getController();
            controller.initAttributes(p);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    void initAttributes(SalesModel sale) throws Exception {
        this.lblIdReq.setText("PEDIDO #"+sale.getId());
        this.lblTotal.setText(sale.getTotal());
        this.lblNameClient.setText(sale.getName_client());
        this.lblAddress.setText(sale.getAddress());
        String req = "";
        HttpURLConnection request = Http.request("/sales/products/"+sale.getId(), "", "GET");
        String response = Http.getResponse(request);
        Gson gson = new Gson();

        Schemas.DetailsInvoice[] di = gson.fromJson(response, Schemas.DetailsInvoice[].class);
        for (Schemas.DetailsInvoice ab:
                di){
            req += "Producto: "+ ab.name +"\n";
            req += "Precio Unit: "+ab.price +"\n";
            req += "Total Producto: "+ (ab.price*ab.quantity) +"\n";
            req += "==============================\n";
        }
        this.txfRequest.setText(req);
    }

    public void initAttributesState(String id, String state){
        this.lblIdCode.setText("Estado Pedido #"+id);
        ObservableList listStates = FXCollections.observableArrayList();
        listStates.addAll("PENDIENTE", "EMPACANDO", "ENVIADO", "ENTREGADO");
        this.ddStates.setItems(listStates);
        this.ddStates.setValue(state);
        this.btnCloseModalState.setOnMouseClicked(e -> {
            Stage st = (Stage) btnCloseModalState.getScene().getWindow();
            st.close();
        });
        this.btnUpdateState.setOnMouseClicked(e -> {
            String data = "{" +
                    "\"state\":\""+this.ddStates.getValue().toString()+"\"" +
                    "}";
            try {
                HttpURLConnection request = Http.request("/sales/"+id, data, "PUT");
                String response = Http.getResponse(request);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Producto");
                alert.setHeaderText(null);
                alert.setContentText(response);
                alert.showAndWait()
                        .ifPresent(res -> {
                            System.out.println(res);
                            if (res == ButtonType.OK) {
                                Stage st = (Stage) btnUpdateState.getScene().getWindow();
                                st.close();
                                try {
                                    this.setScenne("sales-manager");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println(data);
        });
    }

}

