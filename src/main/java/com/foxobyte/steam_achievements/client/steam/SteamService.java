package com.foxobyte.steam_achievements.client.steam;

import com.foxobyte.steam_achievements.client.steam.model.*;
import com.foxobyte.steam_achievements.client.steam.response.SteamApiResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamGameSchemaResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamPlayerStatisticsResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.foxobyte.steam_achievements.config.Settings.API_KEY;
import static com.foxobyte.steam_achievements.util.PrettyPrint.print;

@Service
public class SteamService {
    private SteamFeignClient steamFeignClient;

    public SteamService(SteamFeignClient steamFeignClient) {
        this.steamFeignClient = steamFeignClient;
    }

    public SteamOwnedGames fetchSteamOwnedGames(Long steamId) throws Exception {
        try {
            SteamApiResponse<SteamOwnedGames> response = steamFeignClient.getOwnedGames(
                    API_KEY, steamId, "true", "true", "json"
            ).getBody();

            if (response.getResponse() == null)
                throw new Exception("Failed to fetch steam owned games with steam id: " + steamId);

            return response.getResponse();
        } catch (FeignException e) {
            print(e);

            return null;
        }
    }

    public SteamOwnedGames fetchSteamOwnedGamesWithAchievements(Long steamId) throws Exception {
        try {
            SteamApiResponse<SteamOwnedGames> response = steamFeignClient.getOwnedGames(
                    API_KEY, steamId, "true", "true", "json"
            ).getBody();

            if (response.getResponse() == null)
                throw new Exception("Failed to fetch steam owned games with steam id: " + steamId);

            List<SteamGame> ownedGamesWithAchievements = response.getResponse().getGames().stream().filter(steamGame -> {
                try {
                    return fetchSteamGameSchema(steamGame.getAppId()).getAvailableGameStats().getSteamGameAchievements().size() > 0;
                } catch (Exception e) {
                    System.out.println();
                    return false;
                }
            }).toList();

            response.getResponse().setGames(ownedGamesWithAchievements);
            return response.getResponse();
        } catch (Exception e) {
            System.out.println();

            return null;
        }
    }

    public SteamGameSchema fetchSteamGameSchema(Long appId) throws Exception {
        SteamGameSchemaResponse response = steamFeignClient.getSteamGameSchema(
                API_KEY, appId, "english", "json"
        ).getBody();

        if (response.getGame() == null)
            throw new Exception("Failed to fetch steam game schema for appId: " + appId);

        return response.getGame();
    }

    public SteamPlayerDetails fetchSteamPlayerDetails(Long steamId) throws Exception {
        try {
            SteamApiResponse<SteamPlayersDetails> response = steamFeignClient.getSteamPlayersDetails(
                    API_KEY, List.of(steamId), "json"
            ).getBody();

            if (response.getResponse() == null)
                throw new Exception("Failed to fetch steam players details with steam id: " + steamId);

            return response.getResponse().getPlayers().get(0);
        } catch (FeignException e) {
            print(e);

            return null;
        }
    }

    public SteamPlayerStatistics fetchSteamPlayerStatistics(Long steamId, Long appId) throws Exception {
        SteamPlayerStatisticsResponse response = steamFeignClient.getPlayerStats(API_KEY, steamId, appId, "json", "english").getBody();

        if (response.getSteamPlayerStatistics() == null)
            throw new Exception("Failed to fetch steam players statistics with steam id: " + steamId + " for app id: " + appId);

        return response.getSteamPlayerStatistics();
    }
}
