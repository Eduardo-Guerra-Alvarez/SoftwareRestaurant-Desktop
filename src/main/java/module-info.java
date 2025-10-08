module com.eduardo.softwarerestaurantdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.logging;
    requires com.google.gson;


    opens com.eduardo.softwarerestaurantdesktop.dao to com.google.gson, javafx.base;
    opens com.eduardo.softwarerestaurantdesktop.controllerFX to javafx.fxml;
    exports com.eduardo.softwarerestaurantdesktop;
}