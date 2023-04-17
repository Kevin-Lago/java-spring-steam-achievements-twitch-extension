package com.foxobyte.steam_achievements.repo;

import com.foxobyte.steam_achievements.client.steam.model.SteamGame;
import com.foxobyte.steam_achievements.model.Game;

import java.util.List;

public interface GameAchievementDao {
    Game addGameAchievement();

    Game getGame(Long appId);

    List<Game> getPlayerGames(Long steamId);

    List<Game> getAllGames();

    Game updateGame(SteamGame steamGame);

    void deleteGame(Long appId);
}
