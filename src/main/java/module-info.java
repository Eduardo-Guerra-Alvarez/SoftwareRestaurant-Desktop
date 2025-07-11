module com.eduardo.softwarerestaurantdesktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.eduardo.softwarerestaurantdesktop.controllerFX to javafx.fxml;
    exports com.eduardo.softwarerestaurantdesktop;
}