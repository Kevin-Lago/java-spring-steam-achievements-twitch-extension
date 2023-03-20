package com.foxobyte.steam_achievements.client.steam.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foxobyte.steam_achievements.client.steam.model.SteamPlayerStatistics;

public class SteamPlayerStatisticsResponse {
    private final SteamPlayerStatistics steamPlayerStatistics;

    public SteamPlayerStatisticsResponse(
            @JsonProperty("playerstats") SteamPlayerStatistics steamPlayerStatistics) {
        this.steamPlayerStatistics = steamPlayerStatistics;
    }

    public SteamPlayerStatistics getSteamPlayerStatistics() {
        return steamPlayerStatistics;
    }
}
