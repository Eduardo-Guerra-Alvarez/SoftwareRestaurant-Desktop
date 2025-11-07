package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.dao.MenuDAO;
import com.eduardo.softwarerestaurantdesktop.dao.TableDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddOrdersDetails implements Initializable {

    @FXML
    private FlowPane menuContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    private void loadMenu(List<MenuDAO> menuList) {
        menuContainer.getChildren().clear();

        for (MenuDAO menu : menuList) {
            StackPane card = createMenuCard(menu);
            menuContainer.getChildren().add(card);
        }
    }

    // Create the form of the table card
    private StackPane createMenuCard(MenuDAO menu) {
        Rectangle rect = new Rectangle(120, 100);
        rect.setArcHeight(15);
        rect.setArcWidth(15);

        Label label = new Label(menu.getName());
        label.setAlignment(Pos.CENTER);
        Spinner<Integer> spinner = new Spinner<>();

        StackPane stack = new StackPane(rect, label, spinner);
        stack.setAlignment(Pos.CENTER);

        return stack;
    }


}
