module com.example.fxwebclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;

    opens com.example.fxwebclient to javafx.fxml;
    exports com.example.fxwebclient;
}