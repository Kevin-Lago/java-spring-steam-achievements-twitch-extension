package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SteamPlayerAchievement {
    private final String apiName;
    private final Boolean achieved;
    private final String unlockTime;
    private final String name;
    private final String description;

    public SteamPlayerAchievement(
            @JsonProperty("apiname") String apiName,
            @JsonProperty("achieved") Boolean achieved,
            @JsonProperty("unlocktime") String unlockTime,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description
    ) {
        this.apiName = apiName;
        this.achieved = achieved;
        this.unlockTime = unlockTime;
        this.name = name;
        this.description = description;
    }

    public String getApiName() {
        return apiName;
    }

    public Boolean getAchieved() {
        return achieved;
    }

    public String getUnlockTime() {
        return unlockTime;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
