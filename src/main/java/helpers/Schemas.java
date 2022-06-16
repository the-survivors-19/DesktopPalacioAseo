package helpers;

import java.util.Map;

public class Schemas {
    public class ResponseLogin{
        public String token;
        public Map<String, String> user;
    }

    public class Users{
        public Map<String, String> data;
    }
}
