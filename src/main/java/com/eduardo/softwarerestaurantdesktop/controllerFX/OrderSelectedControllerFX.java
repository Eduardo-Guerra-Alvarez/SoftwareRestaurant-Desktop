package com.eduardo.softwarerestaurantdesktop.controllerFX;

import com.eduardo.softwarerestaurantdesktop.api.ApiServiceMenu;
import com.eduardo.softwarerestaurantdesktop.api.ApiServiceOrder;
import com.eduardo.softwarerestaurantdesktop.api.ApiServiceOrderDetail;
import com.eduardo.softwarerestaurantdesktop.api.ApiServiceTable;
import com.eduardo.softwarerestaurantdesktop.dao.MenuDAO;
import com.eduardo.softwarerestaurantdesktop.dao.OrderDAO;
import com.eduardo.softwarerestaurantdesktop.dao.OrderDetailsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.SimpleFormatter;

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
    @FXML
    private FlowPane menuContainer;
    @FXML
    private ComboBox<String> categoryCBox;

    private final ObservableList<OrderDetailsDAO> orderDetailsList = FXCollections.observableArrayList();
    private OrderDAO order;
    private final ObservableList<MenuDAO> menuList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setColumns();
        tableViewOrderDetail.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        getMenuList();
        filterCategory();
    }

    private void filterCategory() {
        categoryCBox.setValue("Todos");

        loadMenuByCategory("Todos");

        categoryCBox.setOnAction(event -> {
            String categorySelected = categoryCBox.getValue();
            loadMenuByCategory(categorySelected);
        });
    }

    public void getOrder() {
        this.order = ApiServiceOrder.getOrderById(this.order.getId());employeeOrder.setText("Empleado: " +  order.getEmployee_name());
        orderNumber.setText("Orden: " +  order.getId());
        dateOrder.setText(order.getCreated_at());
        totalOrder.setText("Total: " + order.getTotal());
        statusOrder.setText(order.getStatus());
        orderDetailsList.setAll(order.getOrderDetails());
        tableViewOrderDetail.setItems(orderDetailsList);
    }

    public void setOrder(OrderDAO order) {
        this.order = order;
        employeeOrder.setText("Empleado: " +  order.getEmployee_name());
        orderNumber.setText("Orden: " +  order.getId());

        String dateString = order.getCreated_at();
        LocalDateTime date = LocalDateTime.parse(dateString);
        String formatter = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        dateOrder.setText(formatter);
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
                    deleteItem(item.getId(), order.getId());
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

    private void deleteItem(Long orderDetailId, Long orderId) {
        String status = ApiServiceOrderDetail.deleteOrderDetail(orderDetailId, orderId);
        System.out.println(status);
        setOrderDetails();
        getOrder();
    }

    public void goToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addOrdersDetails.fxml"));
            Parent newView = loader.load();
            StackPane mainStackPane = MainControllerFX.getInstance().getCentralContent();
            AddOrdersDetails controller = loader.getController();
            controller.setOrderDetail(order.getId());
            mainStackPane.getChildren().setAll(newView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getMenuList() {
        menuList.clear();
        menuList.setAll(ApiServiceMenu.getMenu());
    }


    private void loadMenuByCategory(String category) {
        menuContainer.getChildren().clear();
        menuList.stream()
                .filter(item -> category.equals("Todos") || item.getCategory().equalsIgnoreCase(category))
                .forEach(item -> menuContainer.getChildren().add(createMenuCard(item)));



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
            String status = ApiServiceOrderDetail.postOrderDetail(order.getId(), menu.getId(), orderDetail);
            System.out.println(status);
            setOrderDetails();
            getOrder();
            spinner.getValueFactory().setValue(0);
        });
        card.getChildren().addAll(lblName, spinner, btnAdd);
        return card;
    }

    public void closeOrder() {
        OrderDAO orderClosed = ApiServiceOrder.closeOrder(this.order.getId(), this.order.getTableRestaurant_number());
        System.out.println(orderClosed);
        setOrder(orderClosed);
    }
}
