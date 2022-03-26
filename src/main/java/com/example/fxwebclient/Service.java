package com.example.fxwebclient;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;


public class Service {

    private String country;
    private final String apiKey = "5fcb67986df74e65f3b4f8a2aab9f2fb";
    private String countryCode;

    public Service(String country) {
        this.country = country;

        Locale loc = Arrays.stream(Locale.getAvailableLocales()).filter(l -> l.getDisplayCountry().equals(country)).findAny().orElse(null);
        countryCode = loc.getCountry();
        System.out.println(countryCode);
    }

    public String getWeather(String city) throws IOException, ParseException {

        JSONParser parser = new JSONParser();

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city+ "," + countryCode + "&appid=" + apiKey + "&units=metric");

        String s = "";

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            String line;
            while((line = in.readLine()) != null)
                s += line;
        }

        JSONObject json = (JSONObject) parser.parse(s);

        return json.toJSONString();
    }

    public Double getRateFor(String kod_waluty) throws IOException {


        URL url = new URL("http://www.nbp.pl/kursy/kursya.html");
        String s = "";

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            String line;
            while((line = in.readLine()) != null)
                s += line;
        }
        System.out.println(s);

        return 1.1;

    }

}
