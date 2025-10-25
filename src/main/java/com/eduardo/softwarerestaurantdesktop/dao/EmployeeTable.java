package com.eduardo.softwarerestaurantdesktop.dao;

public class EmployeeTable {
    private Long employeeId;
    private Long tableId;

    public EmployeeTable(Long employeeId, Long tableId) {
        this.employeeId = employeeId;
        this.tableId = tableId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
