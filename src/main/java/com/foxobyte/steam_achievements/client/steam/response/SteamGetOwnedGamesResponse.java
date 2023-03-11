package com.foxobyte.steam_achievements.client.steam.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foxobyte.steam_achievements.client.steam.model.SteamGame;

import java.util.List;

public class SteamGetOwnedGamesResponse {
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
