package com.foxobyte.steam_achievements.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "games_players",
            joinColumns = {@JoinColumn(name = "steam_id")},
            inverseJoinColumns = {@JoinColumn(name = "app_id")})
    @JsonManagedReference(value = "player-games")
    private Set<Game> games;
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "player-achievements")
    private Set<PlayerAchievement> playerAchievements;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "players_game_achievements",
            joinColumns = {@JoinColumn(name = "steam_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_achievement_id")})
    @JsonBackReference(value = "player-game-achievements")
    private Set<GameAchievement> gameAchievements;

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

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> game) {
        this.games = game;
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public Set<PlayerAchievement> getPlayerAchievements() {
        return playerAchievements;
    }

    public void setPlayerAchievements(Set<PlayerAchievement> playerAchievements) {
        this.playerAchievements = playerAchievements;
    }

    public Set<GameAchievement> getGameAchievements() {
        return gameAchievements;
    }

    public void setGameAchievements(Set<GameAchievement> gameAchievements) {
        this.gameAchievements = gameAchievements;
    }

    public void addGameAchievement(GameAchievement gameAchievement) {
        this.gameAchievements.add(gameAchievement);
    }
}
