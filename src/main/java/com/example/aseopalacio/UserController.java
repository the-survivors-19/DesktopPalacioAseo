package com.example.aseopalacio;

import com.google.gson.Gson;
import helpers.Functions;
import helpers.Http;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.UserModel;

import java.io.IOException;
import java.net.HttpURLConnection;

public class UserController extends MenuComponent {
    @FXML
    private Button btnCreateUser;

    @FXML
    private Button btnSaveUser;

    @FXML
    private Button btnCloseModalCreate;

    @FXML
    private TextField txfAddress;

    @FXML
    private TextField txfEmail;

    @FXML
    private TextField txfFullName;

    @FXML
    private TextField txfPhone;

    @FXML
    private TableView<UserModel> tableUsers;

    @FXML
    private TableColumn colActions;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colFullName;

    @FXML
    private TableColumn colPhone;

    @FXML
    private TableColumn colId;

    @FXML
    private TextField txfId;

    private ObservableList<UserModel> listUsers;

    void initialize() throws Exception {
        super.initialize();
        if (btnCreateUser != null) {
            btnCreateUser.setOnMouseClicked(e -> {
                try {
                    this.openModal("user-modal", 282, 531);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        if (btnSaveUser != null) {
            System.out.println(this.txfId);
            btnSaveUser.setOnMouseClicked(e -> {
                String password = Functions.generatePassword();
                String dataForm = "{" +
                        "\"full_name\":\"" + txfFullName.getText() + "\"," +
                        "\"phone\":\"" + txfPhone.getText() + "\"," +
                        "\"address\":\"" + txfAddress.getText() + "\"," +
                        "\"email\":\"" + txfEmail.getText() + "\"," +
                        "\"password\":\"" + password + "\"," +
                        "\"password_confirmation\":\"" + password + "\"" +
                        "}";
                System.out.println(dataForm);
                try {
                    HttpURLConnection request = Http.request("/users", dataForm, "POST");
                    String response = Http.getResponse(request);
                    System.out.println(response);
                    if (request.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        System.out.println("Error");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Creación de usuario");
                        alert.setHeaderText(null);
                        alert.setContentText("Usuario creado correctamente.\n" +
                                "Contraseña generada: " + password);
                        alert.showAndWait()
                                .ifPresent(res -> {
                                    System.out.println(res);
                                    if (res == ButtonType.OK) {
                                        Stage st = (Stage) btnCloseModalCreate.getScene().getWindow();
                                        st.close();
                                        try {
                                            super.setScenne("users");
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
        if (btnCloseModalCreate != null) {
            btnCloseModalCreate.setOnMouseClicked(e -> {
                Stage st = (Stage) btnCloseModalCreate.getScene().getWindow();
                st.close();
            });
        }
        if (tableUsers != null) {
            this.colId.setCellValueFactory(new PropertyValueFactory("id"));
            this.colFullName.setCellValueFactory(new PropertyValueFactory("full_name"));
            this.colPhone.setCellValueFactory(new PropertyValueFactory("phone"));
            this.colAddress.setCellValueFactory(new PropertyValueFactory("address"));
            this.colEmail.setCellValueFactory(new PropertyValueFactory("email"));
            this.colActions.setCellValueFactory(new PropertyValueFactory("actions"));

            this.listUsers = FXCollections.observableArrayList();
            HttpURLConnection request = Http.request("/users", "", "GET");
            String response = Http.getResponse(request);
            Gson gson = new Gson();

            Schemas.Users[] users = gson.fromJson(response, Schemas.Users[].class);
            for (Schemas.Users user :
                    users) {
                UserModel insertUser = new UserModel(
                        user.id,
                        user.full_name,
                        user.phone,
                        user.address,
                        user.email
                );
                this.listUsers.add(insertUser);
            }

            this.tableUsers.setItems(listUsers);
            this.tableUsers.setOnMouseClicked(e -> {
                try {
                    this.editUser();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    public void editUser() throws IOException {
        UserModel p = this.tableUsers.getSelectionModel().getSelectedItem();
        if (p != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("users-modal.fxml"));
            Parent root = loader.load();

            UserController controller = loader.getController();
            controller.initAttributes(p);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    void initAttributes(UserModel user) {
        this.txfFullName.setText(user.getFull_name());
        this.txfPhone.setText(user.getPhone());
        this.txfAddress.setText(user.getAddress());
        this.txfEmail.setText(user.getEmail());
        this.txfId.setText(user.getId());
        btnSaveUser.setOnMouseClicked(e -> {
            String dataForm = "{" +
                    "\"full_name\":\"" + txfFullName.getText() + "\"," +
                    "\"phone\":\"" + txfPhone.getText() + "\"," +
                    "\"address\":\"" + txfAddress.getText() + "\"," +
                    "\"email\":\"" + txfEmail.getText() + "\"" +
                    "}";
            try {
                HttpURLConnection request = Http.request("/users/"+this.txfId.getText(), dataForm, "PUT");
                if (request.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    System.out.println("Error");
                    String response = Http.getResponse(request);
                    System.out.println(response);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Editar usuario");
                    alert.setHeaderText(null);
                    alert.setContentText("Usuario editado correctamente.");
                    alert.showAndWait()
                            .ifPresent(res -> {
                                System.out.println(res);
                                if (res == ButtonType.OK) {
                                    if (States.session.user.get("id").equals(this.txfId.getText())){
                                        try {
                                            States.session.user.replace("full_name", this.txfFullName.getText());
                                            States.session.user.replace("email", this.txfEmail.getText());
                                            States.session.user.replace("phone", this.txfPhone.getText());
                                            States.session.user.replace("address", this.txfAddress.getText());
                                            System.out.println(States.session.user.toString());
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }

                                    }
                                    Stage st = (Stage) btnCloseModalCreate.getScene().getWindow();
                                    st.close();
                                    try {
                                        super.setScenne("users");
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
