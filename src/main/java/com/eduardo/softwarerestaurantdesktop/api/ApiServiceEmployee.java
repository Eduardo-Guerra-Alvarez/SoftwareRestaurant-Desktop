package com.eduardo.softwarerestaurantdesktop.api;

import com.eduardo.softwarerestaurantdesktop.dao.EmployeeDAO;
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

public class ApiServiceEmployee {

    private static final String apiURL = Config.get("base.url") + Config.get("employees.endpoint");
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static List<EmployeeDAO> apiFetcher() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            Type listType = new TypeToken<List<EmployeeDAO>>() {}.getType();
            return gson.fromJson(responseBody, listType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String postEmployee(EmployeeDAO employeeDAO) throws IOException, InterruptedException {
        String json = gson.toJson(employeeDAO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String putEmployee(Long id, EmployeeDAO employeeDAO) throws IOException, InterruptedException {
        String json = gson.toJson(employeeDAO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String deleteEmployee(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL+"/"+id))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
