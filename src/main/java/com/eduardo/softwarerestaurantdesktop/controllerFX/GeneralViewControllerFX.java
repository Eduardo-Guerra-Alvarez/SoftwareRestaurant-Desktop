package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.api.ApiServiceTable;
import com.eduardo.softwarerestaurantdesktop.dao.TableDAO;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class GeneralViewControllerFX {


    @FXML
    private FlowPane tableContainer;

    public void initialize() {
        loadTables(getTables());
    }

    // get all the tables
    private List<TableDAO> getTables() {
        return ApiServiceTable.apiGetTables();
    }

    // load the tables in the stack pane
    private void loadTables(List<TableDAO> tables) {
        tableContainer.getChildren().clear();

        for (TableDAO table : tables) {
            StackPane card = createTableCard(table);
            tableContainer.getChildren().add(card);
        }
    }

    // Create the form of the table card
    private StackPane createTableCard(TableDAO table) {
        Rectangle rect = new Rectangle(120, 100);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        Color color = switch (table.getStatus()) {
            case "Disponible" -> Color.LIGHTGREEN;
            case "Ocupado" -> Color.SALMON;
            case "Reservado" -> Color.LIGHTBLUE;
            default -> Color.LIGHTGREY;
        };

        rect.setFill(color);

        Label label = new Label(
                "Numero: " + table.getId() +
                "\nCapacity: " + table.getCapacity() +
                        "\n" + table.getStatus()
        );
        label.setAlignment(Pos.CENTER);

        StackPane stack = new StackPane(rect, label);
        stack.setAlignment(Pos.CENTER);

        stack.setOnMouseClicked(e -> {
            System.out.println("clicked table " + table.getId());
        });

        return stack;
    }
}
