package com.foxobyte.steam_achievements.client.steam.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foxobyte.steam_achievements.client.steam.model.SteamGameSchema;

public class SteamGameSchemaResponse {
    private final SteamGameSchema game;

    public SteamGameSchemaResponse(
            @JsonProperty("game") SteamGameSchema game
    ) {
        this.game = game;
    }

    public SteamGameSchema getGame() {
        return game;
    }
}
