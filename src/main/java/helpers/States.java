package helpers;

public class States {
    public static Schemas.ResponseLogin session = null;

    public static void logout(){
        States.session = null;
    }
}
