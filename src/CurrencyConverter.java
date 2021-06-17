import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class CurrencyConverter extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CurrencyConverter.fxml")));
        Scene scene = new Scene(root, 556, 188);

        stage.setTitle("Currency Converter");
        stage.setScene(scene);
        stage.show();
    }
}