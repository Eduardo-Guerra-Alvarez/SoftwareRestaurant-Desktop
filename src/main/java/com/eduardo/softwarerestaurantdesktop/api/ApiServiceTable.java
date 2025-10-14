package com.eduardo.softwarerestaurantdesktop.api;

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

public class ApiServiceTable {
    private static final String apiURL = Config.get("base.url") + Config.get("tables.endpoint");
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static List<TableDAO> apiGetTables() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            Type listenType = new TypeToken<List<TableDAO>>() {}.getType();
            return gson.fromJson(responseBody, listenType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String postTable(TableDAO tableDAO) throws IOException, InterruptedException {
        String json = gson.toJson(tableDAO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
        return res.body();
    }

    public static String putTable(Long id, TableDAO tableDAO) throws IOException, InterruptedException {
        String json = gson.toJson(tableDAO);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        return res.body();
    }


    public static String deleteTable(Long id) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiURL+"/"+id))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        return res.body();
    }
}
