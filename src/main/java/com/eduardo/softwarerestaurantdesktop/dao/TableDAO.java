package com.eduardo.softwarerestaurantdesktop.dao;

public class TableDAO {
    private Long id;
    private Integer capacity;
    private String status;

    public TableDAO(Long id, int capacity, String status) {
        this.id = id;
        this.capacity = capacity;
        this.status = status;
    }

    public TableDAO(int capacity, String status) {
        this.capacity = capacity;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TableDAO{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", status='" + status + '\'' +
                '}';
    }
}
