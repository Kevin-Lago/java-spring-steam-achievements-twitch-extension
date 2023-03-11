package com.foxobyte.steam_achievements.service;

import com.foxobyte.steam_achievements.client.steam.SteamFeignClient;
import com.foxobyte.steam_achievements.client.steam.model.SteamGame;
import com.foxobyte.steam_achievements.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    @Autowired
    SteamFeignClient steamFeignClient;

    public List<Game> getGames() {
        return null;
    }

    public List<SteamGame> getSteamGames() {
        return steamFeignClient.getOwnedGames(
                "A4E43909DCFD513F561D0E96A28F82D8",
                "76561198015026212",
                true,
                true,
                null,
                "json"
        ).getBody().getGames();
    }
}
