package com.foxobyte.steam_achievements.client.steam.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SteamApiResponse<T> {
    private final T response;

    public SteamApiResponse(
            @JsonProperty("response") T response
    ) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }
}
