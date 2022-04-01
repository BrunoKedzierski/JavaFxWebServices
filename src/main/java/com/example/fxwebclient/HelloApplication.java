package com.example.fxwebclient;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class HelloApplication extends Application {

    @FXML
    TextField city;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.centerOnScreen();


        stage.show();


    }

    public static void main(String[] args) {

        Service sr = new Service("Poland");
        try {
            String weather = sr.getWeather("warsaw");
            double nbp = sr.getNBPRate();
            double rate = sr.getRateFor("EUR");
            System.out.println(weather);
            System.out.println(nbp);
            System.out.println(rate);
            System.out.println(sr.printWeather());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        launch();




    }
}