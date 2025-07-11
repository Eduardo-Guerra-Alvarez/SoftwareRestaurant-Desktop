/*package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softrestaurant.dao.EmployeeDAO;
import com.eduardo.softrestaurant.entity.Employee;
import com.eduardo.softrestaurant.service.EmployeeService;
import com.eduardo.softrestaurant.util.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class EmployeeControllerFX implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeControllerFX.class);

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<String> roleBox;
    @FXML
    private PasswordField password_hash;
    @FXML
    private ComboBox<String> isActive;
    @FXML
    private TableView<EmployeeDAO> employeeTableView;
    @FXML
    private TableColumn<EmployeeDAO, Long> idCol;
    @FXML
    private TableColumn<EmployeeDAO, String> firstNameCol;
    @FXML
    private TableColumn<EmployeeDAO, String> lastNameCol;
    @FXML
    private TableColumn<EmployeeDAO, String> emailCol;
    @FXML
    private TableColumn<EmployeeDAO, String> phoneCol;
    @FXML
    private TableColumn<EmployeeDAO, String> roleCol;
    @FXML
    private TableColumn<EmployeeDAO, Boolean> statusCol;

    private Long employeeId = null;

    @Autowired
    private EmployeeService employeeService;

    private final ObservableList<EmployeeDAO> employeesList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setColumns();
        listEmployees();
        onClickTable();
        employeeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        employeeTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    private void onClickTable() {
        employeeTableView.setOnMouseClicked(event -> {
            EmployeeDAO selected = employeeTableView.getSelectionModel().getSelectedItem();
            if(selected != null) {
                employeeId = selected.getId();
                firstNameField.setText(selected.getFirstName());
                lastNameField.setText(selected.getLastName());
                emailField.setText(selected.getEmail());
                phoneField.setText(selected.getPhone());
                roleBox.setValue(selected.getRole());
                isActive.setValue(selected.getIsActive() ? "Activo" : "Inactivo");
            }
        });
    }

    private void setColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        // Get status True or False and then we change to Activo or Inactivo
        statusCol.setCellFactory(column -> new TableCell<EmployeeDAO, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if (item) {
                        setText("Activo");
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        setText("Inactivo");
                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }

    private void listEmployees() {
        employeesList.clear();
        employeesList.addAll(employeeService.getAllEmployees());
        employeeTableView.setItems(employeesList);
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        password_hash.clear();
        isActive.cancelEdit();
        employeeId = null;
    }

    public void addEmployee() {
        Employee newEmployee = new Employee();

        String name = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = password_hash.getText();

        if (name == null || name.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Ingresa un Nombre");
            return;
        }
        if (lastName == null || lastName.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Ingresa un Apellido");
            return;
        }
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Ingresa un Correo valido");
            return;
        }
        if (phone == null || !phone.matches("\\d{10}")) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Ingresa 10 numeros");
            return;
        }
        boolean existEmail = employeesList.stream()
                        .noneMatch(employee -> employee.getEmail().equalsIgnoreCase(email));

        if(existEmail) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Correo ya registrado");
            return;
            
        }

        newEmployee.setFirstName(name);
        newEmployee.setLastName(lastName);
        newEmployee.setEmail(email);
        newEmployee.setPhone(phone);
        newEmployee.setRole(roleBox.getValue());
        newEmployee.setIsActive(isActive.getValue().equals("Activo"));
        newEmployee.setPassword_hash(password);

        if(employeeId == null) {
            employeeService.saveEmployee(newEmployee);
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Exitoso", null, "Se creo el empleado exitosamente");
            logger.info("Empleado creado con exito: " + newEmployee.toString());
        } else {
            employeeService.updateEmployee(employeeId, newEmployee);
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Exitoso", null, "Se actualizo el empleado exitosamente");
            logger.info("Empleado actualizado con exito: " + newEmployee.toString());
        }
        listEmployees();
        clearFields();
    }

    public void deleteEmployee() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmacion");
        confirmAlert.setHeaderText("Estas seguro de eliminar a este empleado?");
        confirmAlert.setContentText("Empleado: " + firstNameField.getText());

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            employeeService.removeEmployee(employeeId);
            listEmployees();
            employeeId = null;
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Exitoso", null, "Empleado Eliminado");
        } else {
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Cancelado", null, "Eliminacion cancelada");
        }
    }
}
*/