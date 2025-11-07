package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.UserSession;
import com.eduardo.softwarerestaurantdesktop.api.ApiServiceOrder;
import com.eduardo.softwarerestaurantdesktop.api.ApiServiceTable;
import com.eduardo.softwarerestaurantdesktop.dao.OrderDAO;
import com.eduardo.softwarerestaurantdesktop.dao.TableDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
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
            onTableClicked(table);
        });

        return stack;
    }

    private void onTableClicked(TableDAO tableDAO) {
        if (tableDAO.getStatus().equals("Disponible")) {
            createNewOrder(UserSession.getInstance().getEmployeeId(), tableDAO);
        } else {
            getOrderOpened(tableDAO);
        }
    }


    private void createNewOrder(Long employeeId, TableDAO table) {
        OrderDAO order = ApiServiceOrder.postOrder(employeeId, table.getId());
        if (order != null) {
            System.out.println(order);
            table.setStatus("Ocupado");
            System.out.println(table);
            ApiServiceTable.putTable(table.getId(), table);

            openOrderWindow(order);
        }
    }

    private void getOrderOpened(TableDAO table) {
        OrderDAO order = ApiServiceOrder.getOrderByTableAndStatus(table.getId(), "OPEN");
        openOrderWindow(order);
    }

    private void openOrderWindow(OrderDAO order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/orderSelected.fxml"));
            Parent newView = loader.load();
            StackPane mainStackPane = MainControllerFX.getInstance().getCentralContent();
            OrderSelectedControllerFX controller = loader.getController();
            controller.setOrder(order);
            mainStackPane.getChildren().setAll(newView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
