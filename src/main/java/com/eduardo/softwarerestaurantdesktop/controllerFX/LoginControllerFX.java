package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.LoginResponse;
import com.eduardo.softwarerestaurantdesktop.UserSession;
import com.eduardo.softwarerestaurantdesktop.api.ApiServiceLogin;
import com.eduardo.softwarerestaurantdesktop.util.AlertUtil;
import com.eduardo.softwarerestaurantdesktop.util.LoggerUtil;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class LoginControllerFX implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private static final Logger logger = LoggerUtil.getLogger();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleLogin() throws IOException, InterruptedException {
        String email = emailField.getText();
        String password = passwordField.getText();

        HttpResponse<String> response  = ApiServiceLogin.login(email, password);

        if(response.statusCode() != 200) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", "", "Login failed");
            logger.severe("Login failed: " + response.body());
            return;
        }


        Map<String, Object> map = new Gson().fromJson(response.body(), Map.class);

        if (map.containsKey("Error")) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", "", "User not found");
            logger.severe("User not found: " + map.get("Error"));
            return;
        }

        String getToken = (String) map.get("token");
        String getEmail = (String) map.get("email");
        Double getEmployeeId = (Double) map.get("employeeId");

        UserSession.initSession(
                getEmail,
                getToken,
                getEmployeeId.longValue()
        );

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("SoftRestaurant - Main");
        stage.setScene(new Scene(root));
        stage.show();

        // Close the login window
        Stage currentStage = (Stage) emailField.getScene().getWindow();
        currentStage.close();
    }
}