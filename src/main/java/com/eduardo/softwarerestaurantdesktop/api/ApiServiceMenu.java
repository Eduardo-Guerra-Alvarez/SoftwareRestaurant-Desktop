package com.eduardo.softwarerestaurantdesktop.api;

import com.eduardo.softwarerestaurantdesktop.dao.MenuDAO;
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

public class ApiServiceMenu {
    private static final String apiURL = Config.get("base.url") + Config.get("menu.endpoint");
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static List<MenuDAO> getMenu() {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            String responseBody = resp.body();

            Type listenType = new TypeToken<List<MenuDAO>>() {}.getType();
            return gson.fromJson(responseBody, listenType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String postMenu(MenuDAO menu) {
        String json = gson.toJson(menu);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            return res.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static String putMenu(Long id, MenuDAO menu) {
        String json = gson.toJson(menu);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            return res.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static String deleteMenu(Long id) {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiURL+"/"+id))
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
