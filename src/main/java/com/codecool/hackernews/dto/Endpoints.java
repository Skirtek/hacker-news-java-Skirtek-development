package com.codecool.hackernews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Endpoints {
    HACKSON_NEWS("news"),
    TOP_NEWS("top"),
    NEWEST("newest"),
    ASK("ask"),
    JOBS("jobs");

    private final String name;

    public static Optional<Endpoints> getEndpointByName(String name) {
        return Arrays.stream(values())
                .filter(e -> e.getName().equals(name))
                .findFirst();

/*        for (Endpoints endpoint : values()) {
            if (endpoint.getName().equals(name)) {
                return Optional.of(endpoint);
            }
        }

        return Optional.empty();*/
    }
}
