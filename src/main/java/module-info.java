module com.example.fxwebclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires javafx.web;
    requires org.controlsfx.controls;

    opens com.example.fxwebclient to javafx.fxml;
    exports com.example.fxwebclient;
}