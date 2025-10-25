package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.api.ApiServiceOrder;
import com.eduardo.softwarerestaurantdesktop.dao.OrderDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderControllerFX implements Initializable {

    @FXML
    private TableView<OrderDAO> orderTableView;
    @FXML
    private TableColumn<OrderDAO, Long> idCol;
    @FXML
    private TableColumn<OrderDAO, String> statusCol;
    @FXML
    private TableColumn<OrderDAO, String> dateCol;
    @FXML
    private TableColumn<OrderDAO, Float> totalCol;
    private TableColumn<OrderDAO, String> employeeCol;

    private final ObservableList<OrderDAO> ordersList = FXCollections.observableArrayList();
    private final Long tableId;

    public OrderControllerFX(Long tableId) {
        this.tableId = tableId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setColumns();
        listOrders();
    }

    private void setColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        employeeCol.setCellValueFactory(new PropertyValueFactory<>("employee_name"));
    }

    private void listOrders() {
        List<OrderDAO> orders = ApiServiceOrder.getOrdersByTable(tableId);
        ordersList.setAll(orders);
        orderTableView.setItems(ordersList);
    }
}
