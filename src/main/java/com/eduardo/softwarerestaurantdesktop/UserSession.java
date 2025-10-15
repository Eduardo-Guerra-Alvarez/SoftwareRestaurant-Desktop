package com.eduardo.softwarerestaurantdesktop;

public class UserSession {

    private static UserSession instance;

    private String email;
    private String token;
    private Long employeeId;

    private UserSession(String email, String token, Long employeeId) {
        this.email = email;
        this.token = token;
        this.employeeId = employeeId;
    }

    public static void initSession(String email, String token, Long employeeId){
        if(instance == null){
            instance = new UserSession(email, token, employeeId);
        }
    }

    public static UserSession getInstance(){
        if (instance == null){
            throw new IllegalStateException("UserSession not initialized!");
        }
        return instance;
    }

    public static void clearSession() {
        instance = null;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public Long getEmployeeId() {
        return employeeId;
    }
}
