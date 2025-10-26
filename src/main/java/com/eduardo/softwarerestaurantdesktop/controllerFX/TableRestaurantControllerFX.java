package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.api.ApiServiceTable;
import com.eduardo.softwarerestaurantdesktop.dao.TableDAO;
import com.eduardo.softwarerestaurantdesktop.util.AlertUtil;
import com.eduardo.softwarerestaurantdesktop.util.LoggerUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class TableRestaurantControllerFX implements Initializable {
    private static final Logger logger = LoggerUtil.getLogger();

    @FXML
    private TextField capacity;

    @FXML
    private ComboBox<String> status;

    @FXML
    private TableView<TableDAO> tableRestaurantView;
    @FXML
    private TableColumn<TableDAO, Long> tableIdCol;
    @FXML
    private TableColumn<TableDAO, String> capacityCol;

    @FXML
    private TableColumn<TableDAO, String> statusCol;

    private final ObservableList<TableDAO> tableList = FXCollections.observableArrayList();

    private Long id;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableRestaurantView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableRestaurantView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setColumns();
        listTables();
        fieldFormatterNumer();
        onClickTable();
    }

    private void onClickTable() {
        tableRestaurantView.setOnMouseClicked(event -> {
            TableDAO selected = tableRestaurantView.getSelectionModel().getSelectedItem();
            if(selected != null) {
                id = selected.getId();
                capacity.setText(String.valueOf(selected.getCapacity()));
                status.setValue(selected.getStatus());
            }
        });
    }

    private void fieldFormatterNumer() {
        TextFormatter<String> numberFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if(newText.matches("\\d*")) {
                return change;
            }
            return null;
        });

        capacity.setTextFormatter(numberFormatter);
    }

    private void setColumns() {
        tableIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    private void listTables() {
        tableList.clear();
        tableList.addAll(ApiServiceTable.apiGetTables());
        tableRestaurantView.setItems(tableList);
    }

    private void clearFields() {
        capacity.clear();
        status.cancelEdit();
    }

    public void addTable() throws IOException, InterruptedException {
        int getCapacity = Integer.parseInt(capacity.getText());
        String getStatus = status.getValue();
        TableDAO tableRestaurant = new TableDAO(
                getCapacity,
                getStatus
        );

        if(capacity == null){
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "error", "", "Ingresa la capacidad");
            logger.severe("Error, capacity null");
            return;
        }

        String table = ApiServiceTable.postTable(tableRestaurant);
        logger.info("Table added successfully. " + table);

        listTables();
        clearFields();
    }

    public void editTable() throws IOException, InterruptedException {
        int getCapacity = Integer.parseInt(capacity.getText());
        String getStatus = status.getValue();
        TableDAO tableRestaurant = new TableDAO(
                getCapacity,
                getStatus
        );

        if(capacity == null){
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "error", "", "Ingresa la capacidad");
            logger.severe("Error, capacity null");
            return;
        }

        if(id != null) {
            TableDAO table = ApiServiceTable.putTable(id, tableRestaurant);
            logger.info("Table updated successfully. " + table);
            id = null;
        }

        listTables();
        clearFields();
    }

    public void deleteTable() throws IOException, InterruptedException {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmacion");
        confirmAlert.setContentText("Estas seguro de eliminar esta Mesa?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            ApiServiceTable.deleteTable(id);
            listTables();
            id = null;
            clearFields();
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Exitoso", null, "Tabla eliminada");
            logger.info("Table deleted.");
        } else {
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Cancelado", null, "Eliminacion cancelada");
            logger.warning("Option canceleted, tabled didn't deleted.");
        }
    }
}
