package com.foxobyte.steam_achievements.service;

import com.foxobyte.steam_achievements.client.steam.SteamFeignClient;
import com.foxobyte.steam_achievements.client.steam.model.SteamGame;
import com.foxobyte.steam_achievements.client.steam.query.SteamGetOwnedGamesQuery;
import com.foxobyte.steam_achievements.client.steam.response.SteamGetOwnedGamesResponse;
import com.foxobyte.steam_achievements.model.Achievement;
import com.foxobyte.steam_achievements.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    @Autowired
    SteamFeignClient steamFeignClient;

    public List<Game> getGames() {
        List<Game> games = new ArrayList<>();
        games.add(new Game("Garry's Mod", "abcd", new ArrayList<>()));
        games.add(new Game("Half Life", "abcd", new ArrayList<>()));
        games.add(new Game("Half Life 2", "abcd", new ArrayList<>()));

        return games;
    }

    public List<SteamGame> getSteamGames() {
        SteamGetOwnedGamesQuery steamGetOwnedGamesQuery = new SteamGetOwnedGamesQuery();
        steamGetOwnedGamesQuery.setApiKey("A4E43909DCFD513F561D0E96A28F82D8");
        steamGetOwnedGamesQuery.setSteamId( "76561198015026212");
        steamGetOwnedGamesQuery.setIncludeAppInfo(true);
        steamGetOwnedGamesQuery.setIncludePlayerFreeGames(true);
        steamGetOwnedGamesQuery.setFormat("json");

        ResponseEntity<SteamGetOwnedGamesResponse> response = steamFeignClient.getOwnedGames(
                steamGetOwnedGamesQuery
        );

        return response.getBody().getGames();
    }
}
