package com.eduardo.softwarerestaurantdesktop.api;

import com.eduardo.softwarerestaurantdesktop.dao.EmployeeDAO;
import com.eduardo.softwarerestaurantdesktop.dao.EmployeeTable;
import com.eduardo.softwarerestaurantdesktop.dao.OrderDAO;
import com.eduardo.softwarerestaurantdesktop.dao.TableDAO;
import com.eduardo.softwarerestaurantdesktop.util.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiServiceOrder {
    private static final String apiURL = Config.get("base.url") + Config.get("orders.endpoint");
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static List<OrderDAO> getOrders() {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            Type listenType = new TypeToken<List<OrderDAO>>() {}.getType();
            return gson.fromJson(res.body(), listenType);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<OrderDAO>getOrdersByTable(Long tableId) {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiURL + "/" + tableId))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            Type listenType = new TypeToken<List<OrderDAO>>() {}.getType();
            return gson.fromJson(res.body(), listenType);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static OrderDAO postOrder(Long employeeId, Long tableId) {
        EmployeeTable newEmployeeTable = new EmployeeTable(employeeId, tableId);
        String json = gson.toJson(newEmployeeTable);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
             return gson.fromJson(res.body(), OrderDAO.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
