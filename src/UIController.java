import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class UIController
{
    @FXML
    private TextField textfieldAmountToConvert;
    @FXML
    private ComboBox<String> comboConvertFrom;
    @FXML
    private  ComboBox<String> comboConvertTo;
    @FXML
    private Text textConvertedAmount;

    public void handleConvertButtonAction()
    {

    }
} 