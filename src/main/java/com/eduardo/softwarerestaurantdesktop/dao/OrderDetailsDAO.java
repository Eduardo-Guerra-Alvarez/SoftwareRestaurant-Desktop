package com.eduardo.softwarerestaurantdesktop.dao;

public class OrderDetailsDAO {
    private Long id;
    private String menu;
    private Integer quantity;
    private Float unit_price;
    private Float subtotal;
    private Long menuId;

    public OrderDetailsDAO(Long id, String menu, Integer quantity, Float unit_price, Float subtotal) {
        this.id = id;
        this.menu = menu;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.subtotal = subtotal;
    }

    public OrderDetailsDAO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Float unit_price) {
        this.unit_price = unit_price;
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "OrderDetailsDAO{" +
                "id=" + id +
                ", menu='" + menu + '\'' +
                ", quantity=" + quantity +
                ", unit_price=" + unit_price +
                ", subtotal=" + subtotal +
                '}';
    }
}
