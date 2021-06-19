import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyAPI
{

    static List<String> getAvailableCurrencyTypes() throws IOException
    {
        URL queryURL = new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json");
        HttpURLConnection connection
                = (HttpURLConnection) queryURL.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        List<String> listOfCurrencies = new ArrayList<>();
        if (responseCode == 200)
        {
            InputStream stream
                    = (InputStream) connection.getContent();
            Scanner scanner = new Scanner(stream);
            Pattern pattern = Pattern.compile("[a-zA-Z]{3}");

            while (scanner.hasNext())
            {
                Matcher matcher = pattern.matcher(scanner.nextLine());
                if (matcher.find())
                {
                    listOfCurrencies.add(matcher.group());
                }
            }
        }
        listOfCurrencies.replaceAll(String::toUpperCase);
        return listOfCurrencies;
    }
}