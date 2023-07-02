package com.codecool.hackernews.integration;

import com.codecool.hackernews.dto.Endpoints;
import com.codecool.hackernews.dto.News;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;

import javax.net.ssl.HttpsURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class HackerApiImpl implements HackerApi {
    private static final Gson gson = new Gson();
    private static final GsonBuilder builder = new GsonBuilder();

    public String getNewsAsJson(Endpoints endpoints, int page) {
        List<News> allNews = readNews(endpoints, page);
        return builder.setPrettyPrinting().create().toJson(allNews);
    }

    @SneakyThrows
    private List<News> readNews(Endpoints endpoints, int page) {
        String urlString = String.format("https://api.hnpwa.com/v0/%s/%d.json", endpoints.getName(), page);

        // file://C:\Users\bmroz\Desktop\mem.jpg
        // postgres://localhost:5432/myDB
        // ftp://localhost:21/myServer
        // https://google.com
        URL url = URI.create(urlString).toURL();

        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestMethod("GET");

        String response = new String(httpsURLConnection.getInputStream().readAllBytes());

        News[] news = gson.fromJson(response, News[].class);

        return Arrays.asList(news);
    }
}
