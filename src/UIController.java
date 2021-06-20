import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;

import java.io.IOException;
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
    public void initialize() throws IOException
    {
        // parse currency name into combo box
        comboConvertFrom.getItems().setAll(CurrencyAPI.getAvailableCurrencyTypes());
        comboConvertTo.getItems().setAll(CurrencyAPI.getAvailableCurrencyTypes());

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

    public void handleConvertButtonAction() throws IOException
    {
        if (comboConvertFrom.getValue() == null ||
                comboConvertTo.getValue() == null ||
                textFieldAmountToConvert.getText().equals(""))
        {
            textConvertedAmount.setText("Please select a currency to convert \n" +
                    "from and a currency to convert to");
        } else
        {
            double exchangeRatio = CurrencyAPI.getExchangeRatio(
                     comboConvertFrom.getValue(), comboConvertTo.getValue());
            if (exchangeRatio == -1)
            {
                textConvertedAmount.setText("Error while trying to retrieve data");
            } else
            {
                double convertedAmount = Double.parseDouble
                        (textFieldAmountToConvert.getText()) * exchangeRatio;
                textConvertedAmount.setText("Converted amount: " +
                        Math.round(convertedAmount * 100.0) / 100.0 +
                        " " +
                        comboConvertTo.getValue());

            }
        }
    }
} 