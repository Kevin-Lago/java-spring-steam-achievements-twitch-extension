package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SteamPlayerStatistic {
    private final String name;
    private final String value;

    public SteamPlayerStatistic(
            @JsonProperty("name") String name,
            @JsonProperty("value") String value
    ) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
