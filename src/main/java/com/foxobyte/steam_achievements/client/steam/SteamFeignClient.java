package com.foxobyte.steam_achievements.client.steam;

import com.foxobyte.steam_achievements.client.steam.query.SteamGetOwnedGamesQuery;
import com.foxobyte.steam_achievements.client.steam.response.SteamGetOwnedGamesResponse;
import feign.Param;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SteamFeignClient", url = "${env.steam.api-domain}")
public interface SteamFeignClient {
    @GetMapping(value = "/IPlayerService/GetOwnedGames/v0001/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SteamGetOwnedGamesResponse> getOwnedGames(
            @SpringQueryMap SteamGetOwnedGamesQuery steamGetOwnedGamesQuery
    );
}
