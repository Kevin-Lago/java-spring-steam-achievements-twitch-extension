package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SteamPlayerStatistics {
    private final String steamId;
    private final String gameName;
    private final List<SteamPlayerStatistic> statistics;
    private final List<SteamPlayerAchievement> achievements;
    private final Boolean success;

    public SteamPlayerStatistics(
            @JsonProperty("steamID") String steamId,
            @JsonProperty("gameName") String gameName,
            @JsonProperty("stats") List<SteamPlayerStatistic> statistics,
            @JsonProperty("achievements") List<SteamPlayerAchievement> achievements,
            @JsonProperty("success") Boolean success
    ) {
        this.steamId = steamId;
        this.gameName = gameName;
        this.statistics = statistics;
        this.achievements = achievements;
        this.success = success;
    }

    public String getSteamId() {
        return steamId;
    }

    public String getGameName() {
        return gameName;
    }

    public List<SteamPlayerStatistic> getStatistics() {
        return statistics;
    }

    public List<SteamPlayerAchievement> getAchievements() {
        return achievements;
    }

    public Boolean getSuccess() {
        return success;
    }
}
