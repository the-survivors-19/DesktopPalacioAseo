package helpers;

import java.util.Map;

public class Schemas {
    public class ResponseLogin{
        public String token;
        public Map<String, String> user;
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
        public Map<String, String> category_id;
        public Map<String, String> provider_id;
    }
}
