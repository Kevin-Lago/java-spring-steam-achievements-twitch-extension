package com.foxobyte.steam_achievements.service;

import com.foxobyte.steam_achievements.client.steam.SteamFeignClient;
import com.foxobyte.steam_achievements.client.steam.model.*;
import com.foxobyte.steam_achievements.client.steam.response.SteamApiResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamGameSchemaResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamPlayerStatisticsResponse;
import com.foxobyte.steam_achievements.dao.Game;
import com.foxobyte.steam_achievements.dao.GameAchievement;
import com.foxobyte.steam_achievements.dao.Player;
import com.foxobyte.steam_achievements.dao.PlayerAchievement;
import com.foxobyte.steam_achievements.repo.GameAchievementRepository;
import com.foxobyte.steam_achievements.repo.GameRepository;
import com.foxobyte.steam_achievements.repo.PlayerAchievementRepository;
import com.foxobyte.steam_achievements.repo.PlayerRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.foxobyte.steam_achievements.config.Settings.API_KEY;
import static com.foxobyte.steam_achievements.util.PrettyPrint.print;
import static java.util.stream.Collectors.toMap;

// ToDo: Persist achievement progress
@Service
public class PlayerService {
    private final SteamFeignClient steamFeignClient;
    private final GameRepository gameRepository;
    private final GameAchievementRepository gameAchievementRepository;
    private final PlayerRepository playerRepository;
    private final PlayerAchievementRepository playerAchievementRepository;

    @Autowired
    public PlayerService(
            SteamFeignClient steamFeignClient,
            GameRepository gameRepository,
            GameAchievementRepository gameAchievementRepository,
            PlayerRepository playerRepository,
            PlayerAchievementRepository playerAchievementRepository
    ) {
        this.steamFeignClient = steamFeignClient;
        this.gameRepository = gameRepository;
        this.gameAchievementRepository = gameAchievementRepository;
        this.playerRepository = playerRepository;
        this.playerAchievementRepository = playerAchievementRepository;
    }

    public Player getPlayer(Long steamId) throws NoSuchElementException {
        Optional<Player> player = playerRepository.findById(steamId);
        if (player.isEmpty()) throw new NoSuchElementException("Couldn't find player with steam id: " + steamId);

        return player.get();
    }

    public Player updatePlayerAchievements(Long steamId) throws Exception {
        Player player = playerRepository.findById(steamId).orElseGet(() -> {
            try {
                return createPlayer(steamId);
            } catch (Exception e) {
                print(e);
                return null;
            }
        });

        List<Game> games = fetchSteamOwnedGames(steamId).getGames().stream().limit(3).map(steamGame ->
                gameRepository.findById(steamGame.getAppId()).orElseGet(() -> {
                    try {
                        return createGame(steamGame);
                    } catch (Exception e) {
                        print(e);
                        return null;
                    }
                })
        ).filter(Objects::nonNull).toList();

        // ToDo: Consider rewriting this
        games.forEach(game -> {
            Map<String, GameAchievement> gameAchievements = game.getAchievements().stream().collect(toMap(GameAchievement::getSteamName, Function.identity()));

            try {
                List<PlayerAchievement> playerAchievements = fetchSteamPlayerStatistics(steamId, game.getAppId()).getAchievements().stream().map(e -> {
                    Long gameAchievementId = gameAchievements.get(e.getApiName()).getId();

                    return playerAchievementRepository.findByAchievedAndUnlockTimeAndGameAchievementIdAndPlayer(
                            e.getAchieved(), e.getUnlockTime(), gameAchievementId, player
                    ).orElseGet(() ->
                            buildPlayerAchievement(e, gameAchievements.get(e.getApiName()), player)
                    );
                }).toList();

                playerAchievementRepository.saveAll(playerAchievements);
            } catch (Exception e) {
                print(e);
            }

            player.addGame(gameRepository.save(game));
            playerRepository.save(player);
        });

        return player;
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
                    gameAchievementRepository.findBySteamNameAndGame(steamGameAchievement.getName(), game).orElse(buildGameAchievement(steamGameAchievement, game))
            ).collect(Collectors.toSet());

            gameAchievementRepository.saveAll(gameAchievements);
            game.setAchievements(gameAchievements);

            return game;
        }

        return null;
    }

    private PlayerAchievement buildPlayerAchievement(SteamPlayerAchievement steamPlayerAchievement, GameAchievement gameAchievement, Player player) {
        PlayerAchievement playerAchievement = new PlayerAchievement();

        playerAchievement.setAchieved(steamPlayerAchievement.getAchieved());
        playerAchievement.setUnlockTime(steamPlayerAchievement.getUnlockTime());
        playerAchievement.setGameAchievement(gameAchievement);
        playerAchievement.setPlayer(player);

        return playerAchievement;
    }

    private GameAchievement buildGameAchievement(SteamGameAchievement steamGameAchievement, Game game) {
        GameAchievement gameAchievement = new GameAchievement();
        gameAchievement.setSteamName(steamGameAchievement.getName());
        gameAchievement.setDisplayName(steamGameAchievement.getDisplayName());
        gameAchievement.setDescription(steamGameAchievement.getDescription());
        gameAchievement.setIcon(steamGameAchievement.getIcon());
        gameAchievement.setIconGray(steamGameAchievement.getIconGray());
        gameAchievement.setGame(game);

        return gameAchievement;
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
