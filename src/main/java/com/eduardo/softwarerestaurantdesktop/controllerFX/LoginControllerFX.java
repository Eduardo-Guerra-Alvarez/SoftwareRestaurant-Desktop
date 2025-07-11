/*package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softrestaurant.entity.Employee;
import com.eduardo.softrestaurant.service.EmployeeService;
import com.eduardo.softrestaurant.util.AlertUtil;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class LoginControllerFX implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ApplicationContext context;

    private static final Logger logger = LoggerFactory.getLogger(LoginControllerFX.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleLogin(ActionEvent event) throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        Optional<Employee> employee = employeeService.getEmployeeByEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(employee.isPresent() && encoder.matches(password, employee.get().getPassword_hash())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
            loader.setControllerFactory(context::getBean); // this is used to communicate with Springboot
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Empleado no valido", "", "El Correo o Contraseña no coinciden");
        }
    }
}
*/