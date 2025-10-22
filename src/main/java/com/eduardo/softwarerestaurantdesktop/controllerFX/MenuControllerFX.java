package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.api.ApiServiceMenu;
import com.eduardo.softwarerestaurantdesktop.dao.MenuDAO;
import com.eduardo.softwarerestaurantdesktop.util.AlertUtil;
import com.eduardo.softwarerestaurantdesktop.util.LoggerUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MenuControllerFX implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private ComboBox<String> categoryField;
    @FXML
    private ComboBox<String> isActive;

    @FXML
    private TableView<MenuDAO> menuTableView;
    @FXML
    private TableColumn<MenuDAO, Long> idCol;
    @FXML
    private TableColumn<MenuDAO, String> nameCol;
    @FXML
    private TableColumn<MenuDAO, String> descriptionCol;
    @FXML
    private TableColumn<MenuDAO, Float> priceCol;
    @FXML
    private TableColumn<MenuDAO, String> categoryCol;
    @FXML
    private TableColumn<MenuDAO, Boolean> statusCol;


    private final ObservableList<MenuDAO> menuList = FXCollections.observableArrayList();
    private static final Logger logger = LoggerUtil.getLogger();
    private Long menuId = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setColumns();
        listMenu();
        onClickTable();
        formatPrice();
        menuTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        menuTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    private void formatPrice() {
        TextFormatter<Float> floatTextFormatter = new TextFormatter<>(
                change -> {
                    if(change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                        return change;
                    } else {
                        return null;
                    }
                }
        );

        priceField.setTextFormatter(floatTextFormatter);
    }

    private void onClickTable() {
        menuTableView.setOnMouseClicked(event -> {
            MenuDAO selected = menuTableView.getSelectionModel().getSelectedItem();
            if(selected != null) {
                menuId = selected.getId();
                nameField.setText(selected.getName());
                descriptionField.setText(selected.getDescription());
                priceField.setText(String.valueOf(selected.getPrice()));
                categoryField.setValue(selected.getCategory());
                isActive.setValue(selected.getIsActive() ? "Activo" : "Inactivo");
            }
        });
    }

    private void setColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));

        statusCol.setCellFactory(column -> new TableCell<MenuDAO, Boolean>() {
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

    private void listMenu() {
        menuList.clear();
        menuList.addAll(ApiServiceMenu.getMenu());
        menuTableView.setItems(menuList);
    }

    private void clearFields() {
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        categoryField.cancelEdit();
        isActive.cancelEdit();
        menuId = null;
    }

    public void addMenu() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String priceString = priceField.getText();
        String category = categoryField.getValue();
        String isActiveResult = isActive.getValue();

        boolean isValidFields = validateFields(name, description, priceString, category);
        if (isValidFields) { return; }

        MenuDAO newMenu = new MenuDAO(name, description, Float.parseFloat(priceString), category, isActiveResult.equals("Activo"));

        String status = ApiServiceMenu.postMenu(newMenu);
        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Info", "", "Articulo creado exitosamente");
        logger.info("Item created successfully: " + status);
        listMenu();
        clearFields();
    }

    public void editMenu() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String priceString = priceField.getText();
        String category = categoryField.getValue();
        String isActiveResult = isActive.getValue();

        if (menuId == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", "", "Articulo no seleccionado");
            logger.severe("item not selected");
            return;
        }

        boolean isValidFields = validateFields(name, description, priceString, category);
        if (isValidFields) { return; }

        MenuDAO newMenu = new MenuDAO(menuId, name, description, Float.parseFloat(priceString), category, isActiveResult.equals("Activo"));

        String status = ApiServiceMenu.putMenu(menuId, newMenu);
        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Info", "", "Articulo actualizado exitosamente");
        logger.info("item updated successfully: " + status);
        listMenu();
        clearFields();

    }

    public void deleteMenu() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmacion");
        confirmAlert.setHeaderText("Estas seguro de eliminar este Menu?");
        confirmAlert.setContentText("Menu: " + nameField.getText());

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            String status = ApiServiceMenu.deleteMenu(menuId);
            listMenu();
            clearFields();
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Exitoso", null, "Articulo Eliminado");
            logger.info("item deleted successfully!: " + status);
        } else {
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Cancelado", null, "Eliminacion cancelada");
            logger.severe("Canceled option");
        }
    }

    private boolean validateFields(String name, String description, String price, String category) {
        if(name == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", "", "Nombre vacio");
            logger.severe("Name empty");
            return true;
        }
        else if(description == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", "", "Descripcion vacia");
            logger.severe("Description empty");
            return true;
        }
        if(price == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", "", "Precio vacia");
            logger.severe("Price empty");
            return true;
        }
        if(category == null) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Error", "", "Categoria vacia");
            logger.severe("Category empty");
            return true;
        }
        return false;
    }
}
