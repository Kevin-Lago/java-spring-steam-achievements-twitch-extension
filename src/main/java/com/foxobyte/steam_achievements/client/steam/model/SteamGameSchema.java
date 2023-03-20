package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SteamGameSchema {
    private final String gameName;
    private final String gameVersion;
    private final SteamAvailableGameStats availableGameStats;

    public SteamGameSchema(
            @JsonProperty("gameName") String gameName,
            @JsonProperty("gameVersion") String gameVersion,
            @JsonProperty("availableGameStats") SteamAvailableGameStats availableGameStats) {
        this.gameName = gameName;
        this.gameVersion = gameVersion;
        this.availableGameStats = availableGameStats;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public SteamAvailableGameStats getAvailableGameStats() {
        return availableGameStats;
    }
}
