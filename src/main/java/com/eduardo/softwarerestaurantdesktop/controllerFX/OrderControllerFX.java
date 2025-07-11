/*package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softrestaurant.dao.TableDAO;
import com.eduardo.softrestaurant.service.TableRestaurantService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class OrderControllerFX implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scrollPane;

    @Autowired
    private TableRestaurantService tableRestaurantService;

    private List<TableDAO> tableRestaurantList;

    private final double CARD_WIDTH = 130;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableRestaurantList = tableRestaurantService.getAllTables();
        bindReziseListener();
    }

    private void bindReziseListener() {
        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            showTablesGrid(newVal.doubleValue());
        });

        showTablesGrid(scrollPane.getWidth());
    }

    private void showTablesGrid(double widthAvailable) {
        gridPane.getChildren().clear();
        gridPane.setPadding(new Insets(10));

        int columns = Math.max((int) (widthAvailable / CARD_WIDTH), 1);
        int col = 0;
        int row = 0;

        for(TableDAO tableRestaurant : tableRestaurantList) {
            VBox card = createCardTable(tableRestaurant);
            gridPane.add(card, col, row);
            col++;
            if(col == columns) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createCardTable(TableDAO tableRestaurant) {
        Label lblNumber = new Label("Mesa: " + tableRestaurant.getId());
        Label lblCapacity = new Label("Capacidad " + tableRestaurant.getCapacity());
        Label lblState = new Label("Estado " + tableRestaurant.getStatus());

        VBox card = new VBox(5, lblNumber, lblCapacity, lblState);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));
        card.setStyle(getStyleForState(tableRestaurant.getStatus()));
        card.setPrefSize(150, 80);


        card.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Crear orden para la mesa: " + tableRestaurant.getId(),
                    ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait().ifPresent(btn -> {
                if(btn == ButtonType.OK) {
                    createOrder(tableRestaurant);
                }
            });
        });

        return card;
    }

    private String getStyleForState(String status) {
        return switch (status) {
            case "Ocupado" -> "-fx-background-color: #ff5c33; -fx-text-fill: white;";
            case "Reservado" -> "-fx-background-color: #ff9966; -fx-text-fill: white;";
            default -> "-fx-background-color: #9fff80; -fx-text-fill: white;";
        };
    }

    private void createOrder(TableDAO tableRestaurant) {
        //
    }
}*/
