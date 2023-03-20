package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SteamOwnedGames {
    @JsonProperty("game_count")
    private Integer gameCount;
    private List<SteamGame> games;

    public Integer getGameCount() {
        return gameCount;
    }

    public void setGameCount(Integer gameCount) {
        this.gameCount = gameCount;
    }

    public List<SteamGame> getGames() {
        return games;
    }

    public void setGames(List<SteamGame> games) {
        this.games = games;
    }
}
