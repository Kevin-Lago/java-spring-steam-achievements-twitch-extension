package com.foxobyte.steam_achievements.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxobyte.steam_achievements.client.steam.SteamFeignClient;
import com.foxobyte.steam_achievements.client.steam.model.*;
import com.foxobyte.steam_achievements.client.steam.response.SteamApiResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamGameSchemaResponse;
import com.foxobyte.steam_achievements.dao.GameAchievement;
import com.foxobyte.steam_achievements.dao.Game;
import com.foxobyte.steam_achievements.dao.Player;
import com.foxobyte.steam_achievements.repo.GameAchievementRepository;
import com.foxobyte.steam_achievements.repo.GameRepository;
import com.foxobyte.steam_achievements.repo.PlayerAchievementRepository;
import com.foxobyte.steam_achievements.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.foxobyte.steam_achievements.util.PrettyPrint.print;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Service
public class GameService {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private SteamFeignClient steamFeignClient;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameAchievementRepository gameAchievementRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerAchievementRepository playerAchievementRepository;
    @Value("${env.steam.api-key}")
    private String API_KEY;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> getPlayerGames(Long steamId) {
        Player player = new Player();
        player.setSteamId(steamId);
        return gameRepository.findByPlayers(player);
    }

    public Game createGame(SteamGame steamGame) throws Exception {
        SteamGameSchema steamGameSchema = fetchSteamGameSchema(steamGame.getAppId());
        Optional<List<SteamGameAchievement>> optionalSteamGameAchievements = Optional.ofNullable(steamGameSchema)
                .map(SteamGameSchema::getAvailableGameStats)
                .map(SteamAvailableGameStats::getSteamGameAchievements);

        if (optionalSteamGameAchievements.isPresent()) {
            Game game = new Game();
            game.setAppId(steamGame.getAppId());
            game.setName(steamGame.getName());
            game.setImageUrl("http://media.steampowered.com/steamcommunity/public/images/apps/" + steamGame.getAppId() + "/" + steamGame.getImgIconUrl() + ".jpg");
            gameRepository.save(game);

            Set<GameAchievement> gameAchievements = optionalSteamGameAchievements.get().stream().map(steamGameAchievement ->
                    gameAchievementRepository.findBySteamNameAndGame(steamGameAchievement.getName(), game).orElse(createGameAchievement(steamGameAchievement, game))
            ).collect(Collectors.toSet());

            gameAchievementRepository.saveAll(gameAchievements);
            game.setAchievements(gameAchievements);

            return game;
        }

        return null;
    }

    private GameAchievement createGameAchievement(SteamGameAchievement steamGameAchievement, Game game) {
        GameAchievement gameAchievement = new GameAchievement();
        gameAchievement.setSteamName(steamGameAchievement.getName());
        gameAchievement.setDisplayName(steamGameAchievement.getDisplayName());
        gameAchievement.setDescription(steamGameAchievement.getDescription());
        gameAchievement.setIcon(steamGameAchievement.getIcon());
        gameAchievement.setIconGray(steamGameAchievement.getIconGray());
        gameAchievement.setGame(game);

        return gameAchievement;
    }

    private SteamGameSchema fetchSteamGameSchema(Long appId) throws Exception {
        SteamGameSchemaResponse response = steamFeignClient.getSteamGameSchema(
                API_KEY, appId, "english", "json"
        ).getBody();

        if (response.getGame() == null) throw new Exception("Failed to fetch steam game schema for appId: " + appId);

        return response.getGame();
    }
}
