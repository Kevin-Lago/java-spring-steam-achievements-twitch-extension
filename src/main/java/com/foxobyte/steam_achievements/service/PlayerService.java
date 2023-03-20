package com.foxobyte.steam_achievements.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxobyte.steam_achievements.client.steam.SteamFeignClient;
import com.foxobyte.steam_achievements.client.steam.model.*;
import com.foxobyte.steam_achievements.client.steam.response.SteamApiResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamPlayerStatisticsResponse;
import com.foxobyte.steam_achievements.dao.Game;
import com.foxobyte.steam_achievements.dao.GameAchievement;
import com.foxobyte.steam_achievements.dao.Player;
import com.foxobyte.steam_achievements.dao.PlayerAchievement;
import com.foxobyte.steam_achievements.repo.GameAchievementRepository;
import com.foxobyte.steam_achievements.repo.GameRepository;
import com.foxobyte.steam_achievements.repo.PlayerAchievementRepository;
import com.foxobyte.steam_achievements.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.foxobyte.steam_achievements.util.PrettyPrint.print;
import static java.util.stream.Collectors.toMap;

@Service
public class PlayerService {
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
    @Autowired
    private GameService gameService;
    @Value("${env.steam.api-key}")
    private String API_KEY;

    public Player getPlayer(Long steamId) {
        return playerRepository.findById(steamId).get();
    }

    public Player updatePlayerAchievements(Long steamId) throws Exception {
        Player player = playerRepository.findById(steamId).orElseGet(() -> {
            try {
                return createPlayer(steamId);
            } catch (Exception e) {
                print(e);
            }

            return null;
        });

        SteamOwnedGames steamOwnedGames = fetchSteamOwnedGames(steamId);

        List<Game> games = steamOwnedGames.getGames().stream().limit(3).map(steamGame ->
            gameRepository.findById(steamGame.getAppId()).orElseGet(() -> {
                try {
                    return gameService.createGame(steamGame);
                } catch (Exception e) {
                    print(e);
                }

                return null;
            })
        ).filter(Objects::nonNull).toList();

        // ToDo: Consider rewriting this
        games.forEach(game -> {
            Map<String, GameAchievement> gameAchievements = game.getAchievements().stream().collect(toMap(GameAchievement::getSteamName, Function.identity()));

            try {
                SteamPlayerStatistics steamPlayerStatistics = fetchSteamPlayerStatistics(steamId, game.getAppId());
                List<PlayerAchievement> playerAchievements = steamPlayerStatistics.getAchievements().stream().map(e -> {
                    Long gameAchievementId = gameAchievements.get(e.getApiName()).getId();

                    return playerAchievementRepository.findByAchievedAndUnlockTimeAndGameAchievementIdAndPlayer(
                            e.getAchieved(), e.getUnlockTime(), gameAchievementId, player
                    ).orElseGet(() ->
                            createPlayerAchievement(e, gameAchievements.get(e.getApiName()), player)
                    );
                }).toList();

                playerAchievementRepository.saveAll(playerAchievements);
                player.addGame(gameRepository.save(game));
                playerRepository.save(player);
            } catch (Exception e) {
                print(e);
            }
        });

        return playerRepository.findById(steamId).get();
    }

    private PlayerAchievement createPlayerAchievement(SteamPlayerAchievement steamPlayerAchievement, GameAchievement gameAchievement, Player player) {
        PlayerAchievement playerAchievement = new PlayerAchievement();

        playerAchievement.setAchieved(steamPlayerAchievement.getAchieved());
        playerAchievement.setUnlockTime(steamPlayerAchievement.getUnlockTime());
        playerAchievement.setGameAchievement(gameAchievement);
        playerAchievement.setPlayer(player);

        return playerAchievementRepository.save(playerAchievement);
    }

    private Player createPlayer(Long steamId) throws Exception {
        SteamPlayerDetails steamPlayerDetails = fetchSteamPlayerDetails(steamId);

        Player player = new Player();
        player.setSteamId(steamId);
        player.setSteamName(steamPlayerDetails.getPersonaName());
        player.setAvatarHash(steamPlayerDetails.getAvatarHash());
        player.setTimeCreated(steamPlayerDetails.getTimeCreated());
        player.setLastLogOff(steamPlayerDetails.getLastLogOff());
        player.setProfileUrl(steamPlayerDetails.getProfileUrl());
        player.setGames(new HashSet<>());

        return playerRepository.save(player);
    }

    private SteamPlayerDetails fetchSteamPlayerDetails(Long steamId) throws Exception {
        SteamApiResponse<SteamPlayersDetails> response = steamFeignClient.getSteamPlayersDetails(API_KEY, List.of(steamId), "json").getBody();

        if (response.getResponse() == null)
            throw new Exception("Failed to fetch steam players details with steam id: " + steamId);

        return response.getResponse().getPlayers().get(0);
    }

    private SteamPlayerStatistics fetchSteamPlayerStatistics(Long steamId, Long appId) throws Exception {
        SteamPlayerStatisticsResponse response = steamFeignClient.getPlayerStats(API_KEY, steamId, appId, "json", "english").getBody();

        if (response.getSteamPlayerStatistics() == null)
            throw new Exception("Failed to fetch steam players statistics with steam id: " + steamId + " and app id: " + appId);

        return response.getSteamPlayerStatistics();
    }

    private SteamOwnedGames fetchSteamOwnedGames(Long steamId) throws Exception {
        SteamApiResponse<SteamOwnedGames> response = steamFeignClient.getOwnedGames(
                API_KEY, steamId, "true", "true", "json"
        ).getBody();

        if (response.getResponse() == null)
            throw new Exception("Failed to fetch steam owned games with steam id: " + steamId);

        return response.getResponse();
    }
}
