package com.eduardo.softwarerestaurantdesktop.dao;

public class MenuDAO {

    private Long id;
    private String name;
    private String description;
    private float price;
    private String category;
    private boolean isActive;


    public MenuDAO(Long id,
                   String name,
                   String description,
                   float price,
                   String category,
                   boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isActive = isActive;
    }

    public MenuDAO(String name, String description, float price, String category, boolean isActive) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
