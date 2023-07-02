package com.codecool.hackernews.integration;

import com.codecool.hackernews.dto.Endpoints;

public interface HackerApi {
    String getNewsAsJson(Endpoints endpoints, int page);
}
