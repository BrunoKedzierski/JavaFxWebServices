package com.example.fxwebclient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;


public class Service {

    private String country;
    private static final String apiKey = "5fcb67986df74e65f3b4f8a2aab9f2fb";
    private String countryCode;
    private String currency;
    private String currencySymbol;
    private static final  String tableA = "https://api.nbp.pl/api/exchangerates/rates/a/";
    private static final  String tableB = "https://api.nbp.pl/api/exchangerates/rates/b/";
    private JSONObject weather;

    public Service(String country) {
        this.country = country;

        Locale loc = Arrays.stream(Locale.getAvailableLocales()).filter(l -> l.getDisplayCountry().equals(country)).findAny().orElse(null);
        countryCode = loc.getCountry();
        Currency instance = Currency.getInstance(new Locale("", countryCode));
        currency = instance.getCurrencyCode();
        currencySymbol = instance.getSymbol();

    }

    public String getWeather(String city) throws IOException, ParseException {

        JSONParser parser = new JSONParser();

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city+ "," + countryCode + "&appid=" + apiKey + "&units=metric");
        System.out.println(url.toString());

        String s = "";

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            String line;
            while((line = in.readLine()) != null)
                s += line;
        }

        JSONObject json = (JSONObject) parser.parse(s);
        this.weather = json;

        return json.toJSONString();
    }

    public String printWeather(){

       JSONObject json = this.weather;
        JSONObject temperature = (JSONObject) json.get("main");
        JSONObject conditions = (JSONObject) ((JSONArray) json.get("weather")).get(0);
       StringBuilder builder = new StringBuilder().append("Temp: ").append(temperature.get("temp")).append("\n")
               .append("Weather: ").append(conditions.get("description")).append("\n");

       return builder.toString();

    }

    public double  getRateFor(String currency) throws IOException, ParseException {
        if (currency.equals(this.currency))
            return 1.0;
        if(currency == null)
            return  0.0;
        JSONParser parser = new JSONParser();
        URL url = new URL("https://api.exchangerate.host/latest?base="+ currency +"&symbols=" + this.currency);
        String s = "";

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            String line;
            while((line = in.readLine()) != null)
                s += line;
        }
        JSONObject json = (JSONObject) ((JSONObject) parser.parse(s)).get("rates");

        return (double) json.get(this.currency);
    }



    public double getNBPRate() throws IOException, ParseException {
        if (this.currency.equals("PLN"))
            return 1.0;
        JSONParser parser = new JSONParser();

        URL url = new URL(tableA + currency+  "?format=json");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        if(connection.getResponseCode() == 404){

            url = new URL(tableB + currency+  "?format=json");
        }

        String s ="";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {

            String line;
            while((line = in.readLine()) != null)
                s += line;
        }

        JSONObject json = (JSONObject) parser.parse(s);
        JSONArray arr = (JSONArray) json.get("rates");

        json = (JSONObject) arr.get(0);

        return (double) json.get("mid");

    }


    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
