package com.eduardo.softwarerestaurantdesktop.dao;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDAO {
    private Long id;
    private Long tableRestaurant_number;
    private String employee_name;
    private String status; // pending, in_progress, delivered, canceled
    private String created_at;
    private Float total;
    private List<OrderDetailsDAO> orderDetails;

    public OrderDAO(Long id, Long tableRestaurant_number, String employee_name, String status, String created_at, Float total) {
        this.id = id;
        this.tableRestaurant_number = tableRestaurant_number;
        this.employee_name = employee_name;
        this.status = status;
        this.created_at = created_at;
        this.total = total;
    }

    public OrderDAO(Long tableRestaurant_number, String employee_name, String status, String created_at, Float total) {
        this.tableRestaurant_number = tableRestaurant_number;
        this.employee_name = employee_name;
        this.status = status;
        this.created_at = created_at;
        this.total = total;
    }

    public OrderDAO(Long id, Long tableRestaurant_number, String employee_name, String status, String created_at, Float total, List<OrderDetailsDAO> orderDetails) {
        this.id = id;
        this.tableRestaurant_number = tableRestaurant_number;
        this.employee_name = employee_name;
        this.status = status;
        this.created_at = created_at;
        this.total = total;
        this.orderDetails = orderDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTableRestaurant_number() {
        return tableRestaurant_number;
    }

    public void setTableRestaurant_number(Long tableRestaurant_number) {
        this.tableRestaurant_number = tableRestaurant_number;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public List<OrderDetailsDAO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsDAO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderDAO{" +
                "id=" + id +
                ", tableRestaurant_number=" + tableRestaurant_number +
                ", employee_name='" + employee_name + '\'' +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                ", total=" + total +
                '}';
    }
}
