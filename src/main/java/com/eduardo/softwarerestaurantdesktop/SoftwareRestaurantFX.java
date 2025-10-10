package com.eduardo.softwarerestaurantdesktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SoftwareRestaurantFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        Scene scene = new Scene(loader.load());

        //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/styles.css")).toExternalForm());
        stage.setTitle("Restaurant App");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        Platform.exit();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
