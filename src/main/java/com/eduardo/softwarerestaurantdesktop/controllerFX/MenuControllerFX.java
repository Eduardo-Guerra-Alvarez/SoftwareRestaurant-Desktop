package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.dao.MenuDAO;
import com.eduardo.softwarerestaurantdesktop.util.LoggerUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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
    private ComboBox<Boolean> isActive;

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
    private TableColumn<MenuDAO, Boolean> isActiveCol;


    private final ObservableList<MenuDAO> menuList = FXCollections.observableArrayList();
    private static final Logger logger = LoggerUtil.getLogger();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void onClickTable() {
        menuTableView.setOnMouseClicked(event -> {
            MenuDAO selected = menuTableView.getSelectionModel().getSelectedItem();
            if(selected != null) {
                nameCol.setText(selected.getName());
                descriptionCol.setText(selected.getDescription());
                priceCol.setText(String.valueOf(selected.getPrice()));
                categoryCol.setText(selected.getCategory());
                isActiveCol.setText(selected.isActive() ? "Activo" : "Inactivo");
            }
        });
    }

    private void setColumn() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        isActiveCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
    }

    private void listMenu() {

    }

    private void clearFields() {

    }

    private void addMenu() {

    }

    private void editMenu() {

    }

    private void deleteMenu() {

    }
}
