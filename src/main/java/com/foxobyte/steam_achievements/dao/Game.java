package com.foxobyte.steam_achievements.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

// ToDo: Eventually make this to persist progress once I figure out extracting progress
@Entity
@Table(name = "games")
public class Game {
    @Id
    private Long appId;
    private String name;
    private String imageUrl;
    @ManyToMany(mappedBy = "games")
    @JsonBackReference(value = "player-games")
    private Set<Player> players;
    @OneToMany(mappedBy = "game")
    @JsonManagedReference(value = "game-achievements")
    private Set<GameAchievement> achievements;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Long steamId) {
        Player player = this.players.stream().filter(p -> p.getSteamId() == steamId).findFirst().orElse(null);
        if (player != null) {
            this.players.remove(player);
            player.getGames().remove(this);
        }
    }

    public Set<GameAchievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(Set<GameAchievement> achievements) {
        this.achievements = achievements;
    }

    public void addAchievement(GameAchievement gameAchievement) {
        this.achievements.add(gameAchievement);
    }
}
