package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SteamAvailableGameStats {
    private final List<SteamGameAchievement> steamGameAchievements;
    private final List<SteamGameStats> steamGameStats;

    public SteamAvailableGameStats(
            @JsonProperty("achievements") List<SteamGameAchievement> steamGameAchievements,
            @JsonProperty("stats") List<SteamGameStats> steamGameStats) {
        this.steamGameAchievements = steamGameAchievements;
        this.steamGameStats = steamGameStats;
    }

    public List<SteamGameAchievement> getSteamGameAchievements() {
        return steamGameAchievements;
    }

    public List<SteamGameStats> getSteamGameStats() {
        return steamGameStats;
    }
}
