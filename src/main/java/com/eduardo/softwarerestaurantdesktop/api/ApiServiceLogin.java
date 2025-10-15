package com.eduardo.softwarerestaurantdesktop.api;

import com.eduardo.softwarerestaurantdesktop.util.Config;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ApiServiceLogin {

    private static final String apiURL = Config.get("base.url") + Config.get("login.endpoint");
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static HttpResponse<String> login(String email, String password) throws IOException, InterruptedException {
        Map<String, String> mapLogin = new HashMap<>();
        mapLogin.put("email", email);
        mapLogin.put("password", password);

        String json = gson.toJson(mapLogin);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.send(req, HttpResponse.BodyHandlers.ofString());
    }
}
