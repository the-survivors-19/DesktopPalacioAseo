package helpers;

import java.util.List;
import java.util.Map;

public class Schemas {
    public class ResponseLogin{
        public String token;
        public Map<String, String> user;
    }

    public class DetailsInvoice{
        public String name;
        public int price;
        public int quantity;
    }

    public class Users{
        public String id;
        public String full_name;
        public String phone;
        public String address;
        public String email;
    }

    public class Categories{
        public String id;
        public String description;
    }

    public class Providers{
        public String id;
        public String name;
        public String phone;
        public String address;
        public String duty_manager;
        public String email;
    }

    public class MeasurementUnits{
        public String id;
        public String unit;
        public String abbreviation;
    }

    public class Products{
        public String code;
        public String id;
        public String name;
        public String description;
        public String img_1;
        public String img_2;
        public String img_3;
        public String img_4;
        public Map<String, String> category_id;
        public Map<String, String> provider_id;
        public List<WeightProduct> weight_products;
    }

    public class WeightProduct{
        public String id;
        public String price;
        public String stock;
        public String quantity;
        public String remove;
        public Map<String, String> measurement_unit_id;
        public Map<String, String> product_id;
    }

    public class SalesDetails{
        public String id;
        public String quantity;
        public String price;
    }

    public class Sales{
        public String id;
        public String date;
        public String total;
        public String state;
        public Map<String, String> user_id;
        public String name_client;
        public String address;
        public List<SalesDetails> sales_details;
    }
}
