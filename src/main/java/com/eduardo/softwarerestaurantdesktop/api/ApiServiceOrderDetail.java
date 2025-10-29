package com.eduardo.softwarerestaurantdesktop.api;

import com.eduardo.softwarerestaurantdesktop.dao.OrderDetailsDAO;
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

public class ApiServiceOrderDetail {
    private static final String apiUrl = Config.get("base.url") + Config.get("orderdetails.endpoint");
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static List<OrderDetailsDAO> getAllOrderDetailsByOrder(Long id) {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/order/" + id))
                .header("Content-Type", "application/json")
                .GET().build();
        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<List<OrderDetailsDAO>>() {}.getType();
            return gson.fromJson(res.body(), listType);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String deleteOrderDetail(Long id) {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/" + id))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            return res.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
