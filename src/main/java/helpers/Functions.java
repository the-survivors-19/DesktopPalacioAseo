package helpers;

public class Functions {
    public static String generatePassword(){
        StringBuffer pass = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int value = (int) (Math.random() * (122 - 97 + 1) + 97);
            pass.append((char) value);
        }
        return  pass.toString();
    }

}
