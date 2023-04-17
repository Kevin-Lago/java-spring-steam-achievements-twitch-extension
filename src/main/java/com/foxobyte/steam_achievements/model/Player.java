package com.foxobyte.steam_achievements.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "players")
public class Player {
    @Id
    private Long steamId;
    private String steamName;
    private String profileUrl;
    private String avatarHash;
    private Long lastLogOff;
    private Long timeCreated;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "games_players",
            joinColumns = {@JoinColumn(name = "steam_id", referencedColumnName = "steamId")},
            inverseJoinColumns = {@JoinColumn(name = "app_id", referencedColumnName = "appId")})
    @JsonManagedReference(value = "player-games")
    private List<Game> games;
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "player-achievements")
    private List<PlayerAchievement> playerAchievements;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "players_game_achievements",
            joinColumns = {@JoinColumn(name = "steam_id", referencedColumnName = "steamId")},
            inverseJoinColumns = {@JoinColumn(name = "game_achievement_id", referencedColumnName = "id")})
    @JsonManagedReference(value = "player-game-achievements")
    private List<GameAchievement> gameAchievements;

    public Long getSteamId() {
        return steamId;
    }

    public void setSteamId(Long steamId) {
        this.steamId = steamId;
    }

    public String getSteamName() {
        return steamName;
    }

    public void setSteamName(String steamName) {
        this.steamName = steamName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getAvatarHash() {
        return avatarHash;
    }

    public void setAvatarHash(String avatarHash) {
        this.avatarHash = avatarHash;
    }

    public Long getLastLogOff() {
        return lastLogOff;
    }

    public void setLastLogOff(Long lastLogOff) {
        this.lastLogOff = lastLogOff;
    }

    public Long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public List<PlayerAchievement> getPlayerAchievements() {
        return playerAchievements;
    }

    public void setPlayerAchievements(List<PlayerAchievement> playerAchievements) {
        this.playerAchievements = playerAchievements;
    }

    public void addPlayerAchievement(PlayerAchievement playerAchievement) {
        this.playerAchievements.add(playerAchievement);
    }

    public List<GameAchievement> getGameAchievements() {
        return gameAchievements;
    }

    public void setGameAchievements(List<GameAchievement> gameAchievements) {
        this.gameAchievements = gameAchievements;
    }

    public void addGameAchievement(GameAchievement gameAchievement) {
        this.gameAchievements.add(gameAchievement);
    }
}
