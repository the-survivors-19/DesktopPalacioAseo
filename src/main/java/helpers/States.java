package helpers;

public class States {
    public static Schemas.ResponseLogin session;

    public static void logout(){
        States.session = null;
    }
}
