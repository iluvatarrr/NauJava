package com.naumen;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientFileDownloader implements FileDownloader {
    private final HttpClient httpClient;

    public HttpClientFileDownloader() {
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public boolean downloadFile(String fileUrl, String outputPath)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileUrl))
                .GET()
                .build();
        HttpResponse<InputStream> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofInputStream());
        if (response.statusCode() != 200) {
            return false;
        }
        try (BufferedInputStream inputStream = new BufferedInputStream(response.body());
             FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return true;
        }
    }
}