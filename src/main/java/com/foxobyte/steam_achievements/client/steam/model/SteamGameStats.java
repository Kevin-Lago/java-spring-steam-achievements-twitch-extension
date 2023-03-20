package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SteamGameStats {
    private final String name;
    private final String defaultValue;
    private final String displayName;

    public SteamGameStats(
            @JsonProperty("name") String name,
            @JsonProperty("defaultvalue") String defaultValue,
            @JsonProperty("displayName") String displayName
    ) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDisplayName() {
        return displayName;
    }
}
