package com.foxobyte.steam_achievements.client.steam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SteamPlayersDetails {
    private final List<SteamPlayerDetails> players;

    public SteamPlayersDetails(
            @JsonProperty("players") List<SteamPlayerDetails> players
    ) {
        this.players = players;
    }

    public List<SteamPlayerDetails> getPlayers() {
        return players;
    }
}
