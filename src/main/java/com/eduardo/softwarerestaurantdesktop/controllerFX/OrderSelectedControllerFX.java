package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.api.ApiServiceOrder;
import com.eduardo.softwarerestaurantdesktop.api.ApiServiceOrderDetail;
import com.eduardo.softwarerestaurantdesktop.dao.OrderDAO;
import com.eduardo.softwarerestaurantdesktop.dao.OrderDetailsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderSelectedControllerFX implements Initializable {
    @FXML
    private Label employeeOrder;
    @FXML
    private Label dateOrder;
    @FXML
    private Label orderNumber;
    @FXML
    private Label totalOrder;
    @FXML
    private Label statusOrder;
    @FXML
    private TableView<OrderDetailsDAO> tableViewOrderDetail;
    @FXML
    private TableColumn<OrderDetailsDAO, Long> idCol;
    @FXML
    private TableColumn<OrderDetailsDAO, String> menuCol;
    @FXML
    private TableColumn<OrderDetailsDAO, Integer> quantityCol;
    @FXML
    private TableColumn<OrderDetailsDAO, Float> unitPriceCol;
    @FXML
    private TableColumn<OrderDetailsDAO, Float> subtotalCol;
    @FXML
    private TableColumn<OrderDetailsDAO, Void> actionCol;

    private final ObservableList<OrderDetailsDAO> orderDetailsList = FXCollections.observableArrayList();
    private OrderDAO order;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setColumns();
        tableViewOrderDetail.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    public void setOrder(OrderDAO order) {
        this.order = order;
        employeeOrder.setText("Empleado: " +  order.getEmployee_name());
        orderNumber.setText("Orden: " +  order.getId());
        dateOrder.setText(order.getCreated_at());
        totalOrder.setText("Total: " + order.getTotal());
        statusOrder.setText(order.getStatus());
        orderDetailsList.setAll(order.getOrderDetails());
        tableViewOrderDetail.setItems(orderDetailsList);
    }

    private void setOrderDetails() {
        orderDetailsList.clear();
        orderDetailsList.setAll(ApiServiceOrderDetail.getAllOrderDetailsByOrder(this.order.getId()));
        tableViewOrderDetail.setItems(orderDetailsList);
    }

    private void setColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        menuCol.setCellValueFactory(new PropertyValueFactory<>("menu"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        addDeleteButtonToTable();
    }

    private void addDeleteButtonToTable() {
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Eliminar");

            {
                deleteButton.getStyleClass().add("buttonRed");

                deleteButton.setOnAction(event -> {
                    OrderDetailsDAO item = getTableView().getItems().get(getIndex());
                    deleteItem(item.getId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(deleteButton);
                    box.setAlignment(Pos.CENTER);
                    setGraphic(box);
                }
            }
        });
    }

    private void deleteItem(Long orderDetailId) {
        String status = ApiServiceOrderDetail.deleteOrderDetail(orderDetailId);
        System.out.println(status);
        setOrderDetails();
    }
}
