import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

    /**
     * Checks which currencies are available for conversion.
     * @return List of ISO 4217 currency codes for the currencies for which conversion is available.
     * @throws IOException Should never happen as the checks are made within the method
     */
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
            // regex parse for currency codes
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

    /**
     * Gets the currency ratio of currencyFrom to currencyTo through an external API
     * @param currencyFrom The 3 letter ISO 4217 code for the currency from which to convert
     * @param currencyTo The 3 letter ISO 4217 code for the currency to which to convert
     * @return If the API call succeeds, returns currency ratio of currencyTo to 1 unit
     * of currencyFrom. Returns -1 if the API call receives any code other than 200
     * @throws IOException Should never happen as the checks are made within the method
     */
    public static double getExchangeRatio(String currencyFrom, String currencyTo) throws IOException
    {
        String queryPath
                = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/"
                + currencyFrom.toLowerCase() + "/"
                + currencyTo.toLowerCase()
                + ".min.json";
        URL queryURL = new URL(queryPath);
        HttpURLConnection connection
                = (HttpURLConnection) queryURL.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == 200)
        {
            InputStream stream
                    = (InputStream) connection.getContent();
            Scanner scanner = new Scanner(stream);
            StringBuilder jsonStringBuilder = new StringBuilder();

            while (scanner.hasNext())
            {
                jsonStringBuilder.append(scanner.nextLine());
            }

            // parse the json object and retrieve the exchange ratio of given currency
            JsonElement element = JsonParser.parseString(jsonStringBuilder.toString());
            return element
                    .getAsJsonObject()
                    .get(currencyTo.toLowerCase())
                    .getAsDouble();

        }
        // if the endpoint is not available and the above statement is not executed, return -1
        return -1;
    }
}