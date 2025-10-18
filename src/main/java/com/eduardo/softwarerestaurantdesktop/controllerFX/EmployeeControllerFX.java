package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.api.ApiServiceEmployee;
import com.eduardo.softwarerestaurantdesktop.dao.EmployeeDAO;
import com.eduardo.softwarerestaurantdesktop.util.AlertUtil;
import com.eduardo.softwarerestaurantdesktop.util.Config;
import com.eduardo.softwarerestaurantdesktop.util.LoggerUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeControllerFX implements Initializable {

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

    private final ObservableList<EmployeeDAO> employeesList = FXCollections.observableArrayList();
    private static final Logger logger = LoggerUtil.getLogger();

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
        List<EmployeeDAO> employees = ApiServiceEmployee.apiFetcher();
        employeesList.addAll(employees);
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

    public void addEmployee() throws IOException, InterruptedException {

        String name = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = password_hash.getText();

        boolean valid = validateFilds(name, lastName, email, phone, password);

        if(valid) {
            return;
        }

        boolean existEmail = employeesList.stream()
                .anyMatch(employee -> employee.getEmail().equalsIgnoreCase(email));

        if(existEmail) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Correo ya registrado");
            logger.log(Level.SEVERE, "email already used");
            return;
        }

        EmployeeDAO newEmployee = new EmployeeDAO(name,
                lastName,
                email,
                phone,
                roleBox.getValue(),
                password,
                isActive.getValue().equals("Activo")
                );

        String status = ApiServiceEmployee.postEmployee(newEmployee);
        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Exitoso", null, "Se creo el empleado exitosamente");
        logger.info("Empleado creado con exito: " + status);
        listEmployees();
        clearFields();
    }

    public void updateEmployee() throws IOException, InterruptedException {
        String name = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = password_hash.getText();

        boolean valid = validateFilds(name, lastName, email, phone, password);

        if(valid) {
            return;
        }

        boolean existEmail = employeesList.stream()
                .anyMatch(employee -> employee.getEmail().equals(email)
                && !Objects.equals(employee.getId(), employeeId));

        if(existEmail) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Correo ya registrado");
            logger.log(Level.SEVERE, "email already used");
            return;
        }

        EmployeeDAO newEmployee = new EmployeeDAO(
                employeeId,
                name,
                lastName,
                email,
                phone,
                roleBox.getValue(),
                password,
                isActive.getValue().equals("Activo")
        );

        if(employeeId == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", null, "Empleado no seleccionado");
            logger.severe("Employee no selected");
            return;
        }

        String status = ApiServiceEmployee.putEmployee(employeeId, newEmployee);
        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Exitoso", null, "Se actualizo el empleado exitosamente");
        logger.info("Empleado actualizado con exito " + status);
        listEmployees();
        clearFields();
    }

    public void deleteEmployee() throws IOException, InterruptedException {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmacion");
        confirmAlert.setHeaderText("Estas seguro de eliminar a este empleado?");
        confirmAlert.setContentText("Empleado: " + firstNameField.getText());

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            String status = ApiServiceEmployee.deleteEmployee(employeeId);
            listEmployees();
            employeeId = null;
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Exitoso", null, "Empleado Eliminado");
            logger.info("Employee added successfully!" + status);
        } else {
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Cancelado", null, "Eliminacion cancelada");
            logger.severe("Canceled option");
        }

        listEmployees();
        clearFields();
    }

    private boolean validateFilds(String name, String lastName, String email, String phone, String password) {
        if (name == null || name.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Ingresa un Nombre");
            logger.log(Level.SEVERE, "Name empty");
            return true;
        }
        if (lastName == null || lastName.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Ingresa un Apellido");
            logger.log(Level.SEVERE, "Last Name empty");
            return true;
        }
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Ingresa un Correo valido");
            logger.log(Level.SEVERE, "Email invalid");
            return true;
        }
        if (phone == null || !phone.matches("\\d{10}")) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "error", null, "Ingresa 10 numeros");
            logger.log(Level.SEVERE, "phone empty");
            return true;
        }

        return false;
    }

}