package com.foxobyte.steam_achievements.service;

import com.foxobyte.steam_achievements.client.steam.SteamFeignClient;
import com.foxobyte.steam_achievements.client.steam.model.*;
import com.foxobyte.steam_achievements.client.steam.response.SteamApiResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamGameSchemaResponse;
import com.foxobyte.steam_achievements.client.steam.response.SteamPlayerStatisticsResponse;
import com.foxobyte.steam_achievements.model.Game;
import com.foxobyte.steam_achievements.model.GameAchievement;
import com.foxobyte.steam_achievements.model.Player;
import com.foxobyte.steam_achievements.model.PlayerAchievement;
import com.foxobyte.steam_achievements.repo.*;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static com.foxobyte.steam_achievements.config.Settings.API_KEY;
import static com.foxobyte.steam_achievements.util.PrettyPrint.print;
import static java.util.stream.Collectors.toMap;

// ToDo: Persist achievement progress
@Service
public class PlayerService {
    private final SteamFeignClient steamFeignClient;
    private final GameJpa gameJpa;
    private final GameAchievementJpa gameAchievementJpa;
    private final PlayerRepository playerRepository;
    private final PlayerAchievementJpa playerAchievementJpa;

    @Autowired
    public PlayerService(
            SteamFeignClient steamFeignClient,
            GameJpa gameJpa,
            GameAchievementJpa gameAchievementJpa,
            PlayerRepository playerRepository,
            PlayerAchievementJpa playerAchievementJpa

    ) {
        this.steamFeignClient = steamFeignClient;
        this.gameJpa = gameJpa;
        this.gameAchievementJpa = gameAchievementJpa;
        this.playerRepository = playerRepository;
        this.playerAchievementJpa = playerAchievementJpa;
    }

    public Player getPlayer(Long steamId) throws NoSuchElementException {
        Optional<Player> player = playerRepository.findById(steamId);
        if (player.isEmpty()) throw new NoSuchElementException("Couldn't find player with steam id: " + steamId);

        return player.get();
    }

    public Player getPlayerAndPlayerGames(Long steamId) {
        Optional<Player> player = playerRepository.findJustPlayerById(steamId);

        if (player.isPresent()) {
            player.get().setGames(gameJpa.findPlayerGamesBySteamId(steamId));

            return player.get();
        }

        return null;
    }

    public List<GameAchievement> getPlayerAchievements(Long steamId, Long appId) {
        Player player = new Player();
        player.setSteamId(steamId);
        Game game = new Game();
        game.setAppId(appId);
        List<GameAchievement> achievements = gameAchievementJpa.findByGameAndPlayers(game, player);

        return null;
    }

    public Player initializePlayer(Long steamId) throws Exception {
        Player player = createPlayerIfNotExist(steamId);
        List<Game> games = fetchSteamOwnedGames(steamId).getGames().stream().limit(4).map(this::createGameIfNotExist).filter(Objects::nonNull).toList();
        games.forEach(this::createGameAchievementsIfNotExists);
        games.forEach(game -> createPlayerAchievementsIfNotExist(player, game));

        return player;
    }

    private List<PlayerAchievement> createPlayerAchievementsIfNotExist(Player player, Game game) {
        Map<String, GameAchievement> gameAchievements = game.getAchievements().stream().collect(toMap(GameAchievement::getSteamName, Function.identity()));

        if (game.getAchievements().size() > 0) {
            try {
                List<PlayerAchievement> playerAchievements = fetchSteamPlayerStatistics(player.getSteamId(), game.getAppId()).getAchievements().stream().map(steamPlayerStatistics -> {
                    GameAchievement gameAchievement = gameAchievements.get(steamPlayerStatistics.getApiName());

                    PlayerAchievement playerAchievement = playerAchievementJpa.findByGameAchievement(gameAchievement)
                            .orElseGet(() -> buildPlayerAchievement(steamPlayerStatistics, gameAchievement, player));

                    player.addGameAchievement(gameAchievement);
                    player.addPlayerAchievement(playerAchievement);
                    return playerAchievement;
                }).toList();

                return playerAchievementJpa.saveAll(playerAchievements);
            } catch (Exception e) {
                System.out.println("nope");
            }
        }

        player.addGame(game);
        playerRepository.save(player);
        return null;
    }

    private Player createPlayerIfNotExist(Long steamId) {
        return playerRepository.findById(steamId).orElseGet(() -> {
            try {
                SteamPlayerDetails steamPlayerDetails = fetchSteamPlayerDetails(steamId);

                Player player = new Player();
                player.setSteamId(steamId);
                player.setSteamName(steamPlayerDetails.getPersonaName());
                player.setAvatarHash(steamPlayerDetails.getAvatarHash());
                player.setTimeCreated(steamPlayerDetails.getTimeCreated());
                player.setLastLogOff(steamPlayerDetails.getLastLogOff());
                player.setProfileUrl(steamPlayerDetails.getProfileUrl());
                player.setGames(new ArrayList<>());

                return playerRepository.save(player);
            } catch (Exception e) {
                print(e);
                return null;
            }
        });
    }

    public Game createGameIfNotExist(SteamGame steamGame) {
        return gameJpa.findById(steamGame.getAppId()).orElseGet(() -> {
            try {
                Game game = new Game();
                game.setAppId(steamGame.getAppId());
                game.setName(steamGame.getName());
                game.setImageUrl("http://media.steampowered.com/steamcommunity/public/images/apps/" + steamGame.getAppId() + "/" + steamGame.getImgIconUrl() + ".jpg");

                return gameJpa.save(game);
            } catch (Exception e) {
                print(e);
                return null;
            }
        });
    }

    private List<GameAchievement> createGameAchievementsIfNotExists(Game game) {
        try {
            SteamGameSchema steamGameSchema = fetchSteamGameSchema(game.getAppId());
            Optional<List<SteamGameAchievement>> optionalSteamGameAchievements = Optional.ofNullable(steamGameSchema)
                    .map(SteamGameSchema::getAvailableGameStats)
                    .map(SteamAvailableGameStats::getSteamGameAchievements);

            if (optionalSteamGameAchievements.isPresent()) {
                List<GameAchievement> gameAchievements = optionalSteamGameAchievements.get().stream().map(steamGameAchievement ->
                        gameAchievementJpa.findBySteamNameAndGame(steamGameAchievement.getName(), game).orElse(buildGameAchievement(steamGameAchievement, game))
                ).toList();

                game.setAchievements(gameAchievements);
                return gameAchievementJpa.saveAll(gameAchievements);
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            print(e);

            return null;
        }
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
