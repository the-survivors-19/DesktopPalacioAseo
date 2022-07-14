package models;

import com.example.aseopalacio.MenuComponent;
import helpers.Http;
import helpers.Schemas;
import javafx.scene.control.Button;

import java.net.HttpURLConnection;
import java.util.List;

public class ProductModel {
    private String id;
    private String name;
    private String code;
    private String img_1;
    private String img_2;
    private String img_3;
    private String img_4;
    private String description;
    private String category_id;
    private String provider_id;
    private List<Schemas.WeightProduct> weight_products;
    private Button actions;

    public ProductModel(String id, String code, String name, String description, String category_id, String provider_id, String img_1, String img_2, String img_3, String img_4) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.category_id = category_id;
        this.provider_id = provider_id;
        this.img_1 = img_1;
        this.img_2 = img_2;
        this.img_3 = img_3;
        this.img_4 = img_4;
        this.actions = new Button("Eliminar");
        this.actions.setOnMouseClicked(e -> {
            try {
                HttpURLConnection request = Http.request("/products/" + id, "", "DELETE");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public Button getActions() {
        return actions;
    }

    public void setActions(Button actions) {
        this.actions = actions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Schemas.WeightProduct> getWeight_products() {
        return weight_products;
    }

    public String getImg_1() {
        return img_1;
    }

    public void setImg_1(String img_1) {
        this.img_1 = img_1;
    }

    public String getImg_2() {
        return img_2;
    }

    public void setImg_2(String img_2) {
        this.img_2 = img_2;
    }

    public String getImg_3() {
        return img_3;
    }

    public void setImg_3(String img_3) {
        this.img_3 = img_3;
    }

    public String getImg_4() {
        return img_4;
    }

    public void setImg_4(String img_4) {
        this.img_4 = img_4;
    }

    public void setWeight_products(List<Schemas.WeightProduct> weight_products) {
        this.weight_products = weight_products;
    }
}
