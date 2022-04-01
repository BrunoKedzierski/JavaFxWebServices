package com.example.fxwebclient;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.controlsfx.control.WorldMapView;
import org.json.simple.parser.ParseException;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Locale;

public class HelloController {

    @FXML
    public TextField city;
    @FXML
    public TextField country;
    @FXML
    public Label display;

    public Stage stage;
    public Scene scene;
    public Parent parent;
    public Label displayWeather;
    public Label displayNBP;
    public TextField rate;
    public Label displayRate;
    public WorldMapView map;

    public void showValues(){
            if(city.getText().isEmpty() ||country.getText().isEmpty()||rate.getText().isEmpty())
                return;
            Service service = new Service(country.getText());
        ObservableList<WorldMapView.Country> selectedCountries = map.getSelectedCountries();
        selectedCountries.add(WorldMapView.Country.valueOf(service.getCountryCode()));
        map.setSelectedCountries(selectedCountries);


        try {
            String weather = service.getWeather(city.getText());
            double NBPrate = service.getNBPRate();
            double rate = service.getRateFor(this.rate.getText());
            displayWeather.setText(service.printWeather());

            displayNBP.setText( String.valueOf(NBPrate) + " " + "PLN");
            displayRate.setText(String.valueOf(rate) + " " +  service.getCurrencySymbol());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void loadWebView(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("web-view.fxml"));


        stage = (Stage) (((Node) mouseEvent.getSource()).getScene().getWindow());
        scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        WebView browser =    (WebView) scene.lookup("#browser");
        TextField urlField = (TextField) scene.lookup("#urlField");
        WebEngine engine = browser.getEngine();
        String url = "https://en.wikipedia.org/wiki/" + city.getText().trim();
        System.out.println(url);
        urlField.setText(url);
        urlField.prefColumnCountProperty().bind(urlField.textProperty().length());
        engine.load(url);
        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(900);
        stage.centerOnScreen();
        stage.show();


    }


    public void LoadHelloView(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        stage = (Stage) (((Node) mouseEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.centerOnScreen();

        stage.show();
    }

    public void selectedCountry(MouseEvent mouseEvent) {

        String code = map.getSelectedCountries().isEmpty() ? "" : map.getSelectedCountries().get(0).toString();
        String name = new Locale("",code).getDisplayName();
        country.setText(name);
    }


}