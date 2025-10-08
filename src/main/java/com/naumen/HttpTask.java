package com.naumen;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class HttpTask implements TaskRunnable {
    private static final HttpClient client = HttpClient.newHttpClient();

    private void getUserAgent() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/user-agent"))
                .build();

        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        response.exceptionally(e -> {
                    System.out.println("Request error: " + e.getMessage());
                    return null;
                })
                .thenAccept(resp -> {
                    if (resp != null) {
                        try {
                            if (resp.statusCode() == 200) {
                                String userAgent = extractUserAgentWithJackson(resp.body());
                                System.out.println(userAgent);
                            } else {
                                System.out.println("Error: HTTP " + resp.statusCode());
                            }
                        } catch (Exception e) {
                            System.out.println("Error processing response: " + e.getMessage());
                        }
                    }
                })
                .join();
    }


    private String extractUserAgentWithJackson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode userAgentNode = rootNode.get("user-agent");
        if (userAgentNode == null) {
            throw new RuntimeException("User-agent field not found in JSON");
        }
        return userAgentNode.asText();
    }

    @Override
    public void run() {
        getUserAgent();
    }
}