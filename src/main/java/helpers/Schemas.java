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
}
