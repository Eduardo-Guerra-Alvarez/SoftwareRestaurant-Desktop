/*package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softrestaurant.dao.TableDAO;
import com.eduardo.softrestaurant.entity.TableRestaurant;
import com.eduardo.softrestaurant.service.TableRestaurantService;
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
public class TableRestaurantControllerFX implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(TableRestaurantControllerFX.class);
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
    private TableColumn<TableRestaurant, String> statusCol;

    @Autowired
    private TableRestaurantService tableRestaurantService;
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
        tableList.addAll(tableRestaurantService.getAllTables());
        tableRestaurantView.setItems(tableList);
    }

    private void clearFields() {
        capacity.clear();
        status.cancelEdit();
    }

    public void addTable() {
        TableRestaurant tableRestaurant = new TableRestaurant();
        tableRestaurant.setCapacity(Integer.valueOf(capacity.getText()));
        tableRestaurant.setStatus(status.getValue());

        if(capacity == null){
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "error", "", "Ingresa la capacidad");
            return;
        }

        if(id != null) {
            tableRestaurantService.updateTable(id, tableRestaurant);
            id = null;
        } else {
            tableRestaurantService.saveTable(tableRestaurant);
        }

        listTables();
        clearFields();
    }

    public void deleteTable() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmacion");
        confirmAlert.setContentText("Estas seguro de eliminar esta Mesa?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            tableRestaurantService.deleteTable(id);
            listTables();
            id = null;
            clearFields();
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Exitoso", null, "Empleado Eliminado");
        } else {
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Cancelado", null, "Eliminacion cancelada");
        }
    }
}
*/