package components.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NonNegativeIntegerTextField extends TextField {

    public NonNegativeIntegerTextField(){
        allowToPassOnlyNonNegativeIntegers();
    }

    private void allowToPassOnlyNonNegativeIntegers() {
        textProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!isNonNegativeInteger(newValue)) {
                    setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private boolean isNonNegativeInteger(String text){
        return text.matches("\\d+");
    }
}