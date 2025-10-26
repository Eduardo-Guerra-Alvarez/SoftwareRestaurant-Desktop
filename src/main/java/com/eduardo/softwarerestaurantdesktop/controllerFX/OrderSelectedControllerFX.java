package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.dao.OrderDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setOrder(OrderDAO order) {
        employeeOrder.setText("Empleado: " +  order.getEmployee_name());
        orderNumber.setText("Orden: " +  order.getId());
        dateOrder.setText(order.getCreated_at());
        totalOrder.setText("Total: " + order.getTotal());
        statusOrder.setText(order.getStatus());
    }
}
