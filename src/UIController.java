import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;

import java.util.function.UnaryOperator;

public class UIController
{
    @FXML
    private TextField textFieldAmountToConvert;
    @FXML
    private ComboBox<String> comboConvertFrom;
    @FXML
    private  ComboBox<String> comboConvertTo;
    @FXML
    public Text textConvertedAmount;

    @FXML
    public void initialize()
    {
        // parse currency name into combo box
        comboConvertFrom.getItems().setAll();
        comboConvertTo.getItems().setAll();

        // restrict textField to integers only, and to a max length of 9
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            int newLength = change.getControlNewText().length();

            if (text.matches("[0-9]*") && newLength < 10) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        textFieldAmountToConvert.setTextFormatter(textFormatter);
    }

    public void handleConvertButtonAction()
    {

    }
} 