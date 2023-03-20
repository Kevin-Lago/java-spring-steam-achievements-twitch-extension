package com.foxobyte.steam_achievements.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "player_achievements")
public class PlayerAchievement {
    @Id
    @GeneratedValue
    private Long id;
    private Boolean achieved;
    private String unlockTime;
    @ManyToOne
    @JoinColumn(name = "steam_id", nullable = false)
    @JsonBackReference(value = "player-achievements")
    private Player player;
    @OneToOne
    @JoinColumn(name = "game_achievement_id", nullable = false)
    @JsonBackReference(value = "player-achievement")
    private GameAchievement gameAchievement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAchieved() {
        return achieved;
    }

    public void setAchieved(Boolean achieved) {
        this.achieved = achieved;
    }

    public String getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(String unlockTime) {
        this.unlockTime = unlockTime;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameAchievement getGameAchievement() {
        return gameAchievement;
    }

    public void setGameAchievement(GameAchievement gameAchievement) {
        this.gameAchievement = gameAchievement;
    }
}
