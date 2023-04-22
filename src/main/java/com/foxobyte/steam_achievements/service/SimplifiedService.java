package com.foxobyte.steam_achievements.service;

import com.foxobyte.steam_achievements.client.steam.SteamFeignClient;
import com.foxobyte.steam_achievements.client.steam.SteamService;
import com.foxobyte.steam_achievements.client.steam.model.*;
import com.foxobyte.steam_achievements.model.Game;
import com.foxobyte.steam_achievements.model.GameAchievement;
import com.foxobyte.steam_achievements.model.Player;
import com.foxobyte.steam_achievements.model.PlayerAchievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SimplifiedService {
    @Autowired
    private SteamService steamService;

    public Player getPlayer(Long steamId) throws Exception {
        SteamPlayerDetails steamPlayerDetails = steamService.fetchSteamPlayerDetails(steamId);
        Player player = buildPlayer(steamPlayerDetails);
        player.setGames(steamService.fetchSteamOwnedGamesWithAchievements(steamId).getGames().stream().map(this::buildGame).toList());

        return player;
    }

    public List<GameAchievement> getPlayerAchievements(Long steamId, Long appId) throws Exception {
        Map<String, GameAchievement> gameAchievements = steamService.fetchSteamGameSchema(appId).getAvailableGameStats().getSteamGameAchievements()
                .stream().map(this::buildGameAchievement).collect(Collectors.toMap(GameAchievement::getSteamName, gameAchievement -> gameAchievement));

        steamService.fetchSteamPlayerStatistics(steamId, appId).getAchievements()
                .stream().forEach(steamPlayerAchievement -> gameAchievements.get(steamPlayerAchievement.getApiName()).setPlayerAchievement(buildPlayerAchievement(steamPlayerAchievement)));

        return gameAchievements.values().stream().toList();
    }

    private PlayerAchievement buildPlayerAchievement(SteamPlayerAchievement steamPlayerAchievement) {
        PlayerAchievement playerAchievement = new PlayerAchievement();
        playerAchievement.setAchieved(steamPlayerAchievement.getAchieved());
        playerAchievement.setUnlockTime(steamPlayerAchievement.getUnlockTime());
        return playerAchievement;
    }

    private GameAchievement buildGameAchievement(SteamGameAchievement steamGameAchievement) {
        GameAchievement gameAchievement = new GameAchievement();
        gameAchievement.setDescription(steamGameAchievement.getDescription());
        gameAchievement.setIcon(steamGameAchievement.getIcon());
        gameAchievement.setDisplayName(steamGameAchievement.getDisplayName());
        gameAchievement.setSteamName(steamGameAchievement.getName());
        gameAchievement.setIconGray(steamGameAchievement.getIconGray());
        return gameAchievement;
    }

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
}
