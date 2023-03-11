package com.foxobyte.steam_achievements.client.steam;

import com.foxobyte.steam_achievements.client.steam.response.SteamGetOwnedGamesResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SteamFeignClient", url = "${env.steam.api-domain}")
public interface SteamFeignClient {
    @GetMapping("/IPlayerService/GetOwnedGames/v0001")
    ResponseEntity<SteamGetOwnedGamesResponse> getOwnedGames(
            @RequestParam("key") String apiKey,
            @RequestParam("steamid") String steamID,
            @RequestParam("include_appinfo") Boolean includeAppInfo,
            @RequestParam("include_played_free_games") Boolean includePlayedFreeGames,
            @RequestParam("appids_filter") Object appIdsFilter,
            @RequestParam("format") String responseFormat
    );
}
