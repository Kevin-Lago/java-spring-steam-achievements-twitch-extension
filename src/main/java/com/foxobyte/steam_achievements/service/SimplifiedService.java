package com.foxobyte.steam_achievements.service;

import com.foxobyte.steam_achievements.client.steam.SteamFeignClient;
import com.foxobyte.steam_achievements.client.steam.model.*;
import com.foxobyte.steam_achievements.client.steam.response.SteamApiResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamGameSchemaResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamPlayerStatisticsResponse;
import com.foxobyte.steam_achievements.model.Game;
import com.foxobyte.steam_achievements.model.GameAchievement;
import com.foxobyte.steam_achievements.model.Player;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.foxobyte.steam_achievements.config.Settings.API_KEY;
import static com.foxobyte.steam_achievements.util.PrettyPrint.print;

@Service
public class SimplifiedService {
    private final SteamFeignClient steamFeignClient;

    public SimplifiedService(SteamFeignClient steamFeignClient) {
        this.steamFeignClient = steamFeignClient;
    }

    public Player getPlayer(Long steamId) throws Exception {
        SteamPlayerDetails steamPlayerDetails = fetchSteamPlayerDetails(steamId);
        Player player = buildPlayer(steamPlayerDetails);
        player.setGames(fetchSteamOwnedGames(steamId).getGames().stream().map(this::buildGame).toList());

        return player;
    }

//    public List<GameAchievement> getGameAchievements(Long steamId, Long appId) {
//        List<>
//    }

    private Player buildPlayer(SteamPlayerDetails steamPlayerDetails) {
        Player player = new Player();
        player.setSteamId(steamPlayerDetails.getSteamId());
        player.setSteamName(steamPlayerDetails.getPersonaName());
        player.setProfileUrl(steamPlayerDetails.getProfileUrl());
        player.setAvatarHash(steamPlayerDetails.getAvatarHash());
        player.setLastLogOff(steamPlayerDetails.getLastLogOff());
        player.setTimeCreated(steamPlayerDetails.getTimeCreated());

        return player;
    }

    private Game buildGame(SteamGame steamGame) {
        Game game = new Game();
        game.setAppId(steamGame.getAppId());
        game.setName(steamGame.getName());
        game.setImageUrl(steamGame.getImgIconUrl());

        return game;
    }

    private SteamOwnedGames fetchSteamOwnedGames(Long steamId) throws Exception {
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

    private SteamGameSchema fetchSteamGameSchema(Long appId) throws Exception {
        SteamGameSchemaResponse response = steamFeignClient.getSteamGameSchema(
                API_KEY, appId, "english", "json"
        ).getBody();

        if (response.getGame() == null)
            throw new Exception("Failed to fetch steam game schema for appId: " + appId);

        return response.getGame();
    }

    private SteamPlayerDetails fetchSteamPlayerDetails(Long steamId) throws Exception {
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

    private SteamPlayerStatistics fetchSteamPlayerStatistics(Long steamId, Long appId) throws Exception {
        SteamPlayerStatisticsResponse response = steamFeignClient.getPlayerStats(API_KEY, steamId, appId, "json", "english").getBody();

        if (response.getSteamPlayerStatistics() == null)
            throw new Exception("Failed to fetch steam players statistics with steam id: " + steamId + " for app id: " + appId);

        return response.getSteamPlayerStatistics();
    }
}
