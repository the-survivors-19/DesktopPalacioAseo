package helpers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class KeyPresseds {
    public static void onlyLetters(final TextField field) {
        field.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZáéíóúÁÉÍÓÚñÑ*")) {
                field.setText(newValue.replaceAll("[^\\sa-zA-ZáéíóúÁÉÍÓÚñÑ]", ""));
            }
        });
    }
    public static void onlyNumbers(final TextField field) {
        field.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    public static void maxLength(final TextField field, int limit) {
        field.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(field.getText().length() > limit){
                String s = field.getText().substring(0, limit);
                field.setText(s);
            }
        });
    }
    public static void withoutSpaces(final TextField field) {
        field.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\s*")){
                field.setText(newValue.replaceAll("[^\\s]", ""));
            }
        });
    }

}
