package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.api.ApiServiceMenu;
import com.eduardo.softwarerestaurantdesktop.api.ApiServiceOrderDetail;
import com.eduardo.softwarerestaurantdesktop.dao.MenuDAO;
import com.eduardo.softwarerestaurantdesktop.dao.OrderDAO;
import com.eduardo.softwarerestaurantdesktop.dao.OrderDetailsDAO;
import com.eduardo.softwarerestaurantdesktop.dao.TableDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddOrdersDetails implements Initializable {

    @FXML
    private FlowPane menuContainer;

    private Long orderId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMenu(getMenuList());
    }
    public void setOrderDetail(Long orderId) {
        this.orderId = orderId;
    }

    private List<MenuDAO> getMenuList() {
        return ApiServiceMenu.getMenu();
    }

    private void loadMenu(List<MenuDAO> menuList) {
        menuContainer.getChildren().clear();

        for (MenuDAO menu : menuList) {
            VBox card = createMenuCard(menu);
            menuContainer.getChildren().add(card);
        }
    }

    // Create the form of the table card
    private VBox createMenuCard(MenuDAO menu) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(120, 160);
        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 12;
                -fx-border-color: #d3d3d3;
                -fx-border-radius: 12;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 2);
                """);

        Label lblName = new Label(menu.getName() + "\n" + menu.getPrice());
        lblName.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        lblName.setWrapText(true);

        Spinner<Integer> spinner = new Spinner<>(0, 10, 0);
        Button btnAdd = new Button("Agregar");
        btnAdd.setStyle("""
            -fx-background-color: #2196F3;
            -fx-text-fill: white;
            """);

        btnAdd.setOnAction(click -> {
            int quantity = spinner.getValue();
            OrderDetailsDAO orderDetail = new OrderDetailsDAO();
            orderDetail.setQuantity(quantity);
            String status = ApiServiceOrderDetail.postOrderDetail(this.orderId, menu.getId(), orderDetail);
            System.out.println(status);
        });
        card.getChildren().addAll(lblName, spinner, btnAdd);
        return card;
    }


}
