package com.foxobyte.steam_achievements.service;

import com.foxobyte.steam_achievements.client.steam.model.SteamGame;
import com.foxobyte.steam_achievements.model.Game;
import com.foxobyte.steam_achievements.repo.GameDao;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.foxobyte.steam_achievements.util.PrettyPrint.print;

@Service
public class GameService {
    GameDao gameJdbc;

    public Game createGameIfNotExist(SteamGame steamGame) {
        return gameJdbc.addGame(steamGame);
    }

    public Game getGame(Long appId) {
        return gameJdbc.getGame(appId);
    }

    public List<Game> getPlayerGames(Long steamId) {
        return gameJdbc.getPlayerGames(steamId);
    }

    public List<Game> getAllGames() {
        return gameJdbc.getAllGames();
    }

    public Game updateGame(SteamGame steamGame) {
        return gameJdbc.updateGame(steamGame);
    }

    public void deleteGame(Long appId) {
        gameJdbc.deleteGame(appId);
    }
}
