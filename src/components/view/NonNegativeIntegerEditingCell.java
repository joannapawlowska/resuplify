package components.view;

import dto.Product;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;

public class NonNegativeIntegerEditingCell extends TableCell<Product, Integer> {

    private final TextField textField = new TextField();
    private UnaryOperator<TextFormatter.Change> filter;
    private StringConverter<Integer> converter;
    private TextFormatter<Integer> textFormatter;

    public NonNegativeIntegerEditingCell() {
        createFilterPassingOnlyNonNegativeIntegers();
        createStringIntegerConverter();
        createTextFormatter();
        addFormatterAndActionToTextField();

        textProperty().bind(Bindings
                .when(emptyProperty())
                .then((String) null)
                .otherwise(itemProperty().asString()));

        setGraphic(textField);
    }

    private void createFilterPassingOnlyNonNegativeIntegers() {
        filter = change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty()) {
                return change;
            }
            if (isNonNegativeInteger(newText)) {
                return change;
            }
            return null;
        };
    }

    private boolean isNonNegativeInteger(String text) {
        return text.matches("\\d+");
    }

    private void createStringIntegerConverter() {
        converter = new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                return integer.toString();
            }

            @Override
            public Integer fromString(String s) {
                if (s == null)
                    return getItem();
                else
                    return Integer.valueOf(s);
            }
        };
    }

    private void createTextFormatter() {
        textFormatter = new TextFormatter<>(converter, 0, filter);
    }

    private void addFormatterAndActionToTextField() {
        textField.setTextFormatter(textFormatter);
        commitEditingOnAction();
        cancelEditingOnKeyEscape();
    }

    private void commitEditingOnAction() {
        textField.setOnAction(event -> {
            commitEdit(converter.fromString(textField.getText()));
        });
    }

    private void cancelEditingOnKeyEscape() {
        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    @Override
    public void commitEdit(Integer newValue) {
        super.commitEdit(newValue);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    protected void updateItem(Integer value, boolean empty) {
        super.updateItem(value, empty);
        if (isEditing()) {
            textField.requestFocus();
            textField.selectAll();
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        textFormatter.setValue(getItem());
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.requestFocus();
        textField.selectAll();
    }
}