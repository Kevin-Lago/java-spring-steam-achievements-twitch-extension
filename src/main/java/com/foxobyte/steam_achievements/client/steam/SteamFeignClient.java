package com.foxobyte.steam_achievements.client.steam;

import com.foxobyte.steam_achievements.client.steam.model.*;
import com.foxobyte.steam_achievements.client.steam.response.SteamApiResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamGameSchemaResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamPlayerStatisticsResponse;
import jakarta.annotation.Nullable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "SteamFeignClient", url = "${env.steam.api-domain}")
public interface SteamFeignClient {
    @GetMapping(value = "/IPlayerService/GetOwnedGames/v0001/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SteamApiResponse<SteamOwnedGames>> getOwnedGames(
            @RequestParam(value = "key") String apiKey,
            @RequestParam(value = "steamId") Long steamId,
            @RequestParam(value = "include_appinfo", required = false, defaultValue = "true") String include_appinfo,
            @RequestParam(value = "include_played_free_games", required = false, defaultValue = "true") String includePlayedFreeGames,
            @RequestParam(value = "format", required = false, defaultValue = "json") @Nullable String format
    );

    @GetMapping(value = "/ISteamUserStats/GetPlayerAchievements/v0001/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SteamPlayerStatisticsResponse> getPlayerStats(
            @RequestParam(value = "key") String apiKey,
            @RequestParam(value = "steamId") Long steamId,
            @RequestParam(value = "appid") Long appId,
            @RequestParam(value = "format") String responseFormat,
            @RequestParam(value = "l") String language
    );

    @GetMapping(value = "/ISteamUserStats/GetSchemaForGame/v0002/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SteamGameSchemaResponse> getSteamGameSchema(
            @RequestParam(value = "key") String apiKey,
            @RequestParam(value = "appid") Long appId,
            @RequestParam(value = "l") String language,
            @RequestParam(value = "format") String responseFormat
    );

    @GetMapping(value = "/ISteamUser/GetPlayerSummaries/v0002/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SteamApiResponse<SteamPlayersDetails>> getSteamPlayersDetails(
            @RequestParam(value = "key") String apiKey,
            @RequestParam(value = "steamIds") List<Long> steamIds,
            @RequestParam(value = "format") String responseFormat
    );
}
