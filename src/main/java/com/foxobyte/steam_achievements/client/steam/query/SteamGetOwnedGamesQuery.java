package com.foxobyte.steam_achievements.client.steam.query;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SteamGetOwnedGamesQuery {
    @JsonProperty("key")
    private String apiKey;
    @JsonProperty("steamid")
    private String steamId;
    @JsonProperty("include_appinfo")
    private Boolean includeAppInfo;
    @JsonProperty("includePlayerFreeGames")
    private Boolean includePlayerFreeGames;
    private String format;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public Boolean getIncludeAppInfo() {
        return includeAppInfo;
    }

    public void setIncludeAppInfo(Boolean includeAppInfo) {
        this.includeAppInfo = includeAppInfo;
    }

    public Boolean getIncludePlayerFreeGames() {
        return includePlayerFreeGames;
    }

    public void setIncludePlayerFreeGames(Boolean includePlayerFreeGames) {
        this.includePlayerFreeGames = includePlayerFreeGames;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
