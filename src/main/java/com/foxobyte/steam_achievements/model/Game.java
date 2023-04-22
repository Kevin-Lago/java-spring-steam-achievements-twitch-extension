package com.foxobyte.steam_achievements.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    void Game() {

    }
    @Id
    private Long appId;
    private String name;
    private String imageUrl;
    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY)
    @JsonBackReference(value = "player-games")
    private List<Player> players;
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "game-achievements")
    private List<GameAchievement> achievements;

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
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

    public List<GameAchievement> getAchievements() {
        if (this.achievements == null) {
            return new ArrayList<>();
        }
        return achievements;
    }

    public void setAchievements(List<GameAchievement> achievements) {
        this.achievements = achievements;
    }

    public void addAchievement(GameAchievement gameAchievement) {
        this.achievements.add(gameAchievement);
    }
}
